package com.lloyds.time.controller;

import com.lloyds.time.exception.HumanReadTimeException;
import com.lloyds.time.service.ITimeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/lloyds")
public class TimeController {

    @Autowired
    private ITimeService timeService;

    @GetMapping("time/")
    @Operation(summary = "Time as human readable format. Takes hh:mm or nothing to get current time!")
    public ResponseEntity<String> fetchReadableTime(@RequestParam(required = false) String time) {
        return new ResponseEntity<>(timeService.getTime(time), HttpStatus.OK);
    }

    /**
     * Simulate making 3 rest call in parallel and getting the combined result at the end.
     */
    @GetMapping("/fetch-3-times/{time1}/{time2}/{time3}")
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public ResponseEntity<List<String>> getThreeTimes(@PathVariable String time1, @PathVariable String time2, @PathVariable String time3) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:8080/lloyds/time/"; // Assuming the application runs on port 8080

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(baseUrl + "?time=" + time1, String.class));
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(baseUrl + "?time=" + time2, String.class));
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(baseUrl + "?time=" + time3, String.class));

        return CompletableFuture.allOf(future1, future2, future3)
                .thenApply(v -> {
                    List<String> times = new ArrayList<>();
                    times.add(future1.join());
                    times.add(future2.join());
                    times.add(future3.join());
                    return ResponseEntity.ok(times);
                }).join();
    }

    @Recover // used by Retryable
    public ResponseEntity<List<String>> getThreeTimesFallback(Exception e, String time1, String time2, String time3) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Please try after some time"));
    }

    @GetMapping("/time-stream")
    @CircuitBreaker(name = "timeStream", fallbackMethod = "streamRandomTimesFallback")
    public SseEmitter streamRandomTimes() {
        SseEmitter emitter = new SseEmitter(-1L);
        CountDownLatch latch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            Thread.startVirtualThread(() -> {
                try {
                    Random random = new Random();
                    int hour = random.nextInt(24);
                    int minute = random.nextInt(60);
                    String time = String.format("%02d:%02d", hour, minute);
                    Thread.sleep(random.nextInt(15000)); // Jitters like random wake-ups in retry add some random delay
                    String humanReadableTime = timeService.getTime(time);
                    emitter.send(SseEmitter.event().data(humanReadableTime));
                } catch (Exception e) {
                    // Error in a single task, e.g., client disconnected.
                    // We can't do much, just let the thread terminate.
                    System.out.println("Exception found : " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // Start a separate thread to wait for all tasks to complete and then close the emitter.
        Thread.startVirtualThread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                emitter.complete();
            }
        });

        return emitter;
    }

    public SseEmitter streamRandomTimesFallback(Exception e) {
        System.out.println("streamRandomTimesFallback : " + e.getMessage());
        SseEmitter emitter = new SseEmitter();
        try {
            emitter.send(SseEmitter.event().data("Please try after some time"));
            emitter.complete();
        } catch (Exception ex) {
            emitter.completeWithError(ex);
        }
        return emitter;
    }

    @ExceptionHandler(value = HumanReadTimeException.class)
    public ResponseEntity timeException(HumanReadTimeException timeException) {
        return new ResponseEntity("Time issue : " + timeException.getLocalizedMessage(), HttpStatus.CONFLICT);
    }
}