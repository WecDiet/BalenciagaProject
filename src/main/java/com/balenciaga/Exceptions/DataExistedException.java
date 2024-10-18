package com.balenciaga.Exceptions;

public class DataExistedException extends RuntimeException {
    public DataExistedException(String message) {
        super(message);
    }
}
