package com.nguyenphucthienan.msscbeerservice.web.controller;

public class MvcNotFoundException extends RuntimeException {

    public MvcNotFoundException(String message) {
        super(message);
    }

    public MvcNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvcNotFoundException(Throwable cause) {
        super(cause);
    }
}
