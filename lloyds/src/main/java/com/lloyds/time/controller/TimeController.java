package com.lloyds.time.controller;

import com.lloyds.time.exception.HumanReadTimeException;
import com.lloyds.time.service.ITimeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(value = HumanReadTimeException.class)
    public ResponseEntity timeException(HumanReadTimeException timeException) {
        return new ResponseEntity("Time issue : " + timeException.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

}
