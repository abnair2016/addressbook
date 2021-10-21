package com.anair.addressbook.exception;

public class InvalidPersonException extends RuntimeException {
    public InvalidPersonException(final String message) {
        super(message);
    }
}
