package com.esoft.carservice.configuration.exception;

public class ServiceException extends RuntimeException {

    private int status;
    private String message;


    public ServiceException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
