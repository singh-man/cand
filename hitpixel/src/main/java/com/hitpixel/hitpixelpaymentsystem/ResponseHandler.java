package com.hitpixel.hitpixelpaymentsystem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    /**
     * A helper function to generate json response to the client
     * @param message message
     * @param status status
     * @param data data
     * @param currentPage current page size
     * @param totalPages total page size
     * @return ResponseEntity
     */
    public static ResponseEntity<Object> generateResponse(String message,
                                                          HttpStatus status,
                                                          List data,
                                                          int currentPage,
                                                          int totalPages ) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.MESSAGE, message);
        map.put(Constants.STATUS, status.value());
        map.put(Constants.DATA, data);
        map.put(Constants.TOTAL_ITEMS, data.size());
        map.put(Constants.CURRENT_PAGE, currentPage);
        map.put(Constants.TOTAL_PAGES, totalPages);

        return new ResponseEntity<Object>(map,status);
    }

    /**
     * Overloaded function to generate json response to the client
     * @param message mnessage
     * @param status status
     * @param data data
     * @return ResponseEntity
     */
    public static ResponseEntity<Object> generateResponse(String message,
                                                          HttpStatus status,
                                                          Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.MESSAGE, message);
        map.put(Constants.STATUS, status.value());
        map.put(Constants.DATA, data);

        return new ResponseEntity<Object>(map,status);
    }
}