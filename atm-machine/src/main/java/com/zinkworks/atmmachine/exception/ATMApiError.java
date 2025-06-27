package com.zinkworks.atmmachine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ATMApiError {

	private HttpStatus status = null;

	private String message = "";

}
