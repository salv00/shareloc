package com.cdad.shareloc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
		value = HttpStatus.UNAUTHORIZED
		)

public class CustomUnauthorizedResponseStatus extends RuntimeException{


	public CustomUnauthorizedResponseStatus(String msg) {
	    super(msg);
	}


	
}
