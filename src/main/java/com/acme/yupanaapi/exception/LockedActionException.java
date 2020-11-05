package com.acme.yupanaapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class LockedActionException extends RuntimeException{
	public LockedActionException() {
        super();
    }

    public LockedActionException(String message) {
        super(message);
    }
}
