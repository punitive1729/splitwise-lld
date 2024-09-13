package org.example.exceptions;

public class SplitWiseServiceException extends Exception {
    private final int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public SplitWiseServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
