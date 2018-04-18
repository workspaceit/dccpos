package com.workspaceit.dccpos.exception;

import java.util.List;
import java.util.Map;

public class ServiceException extends Exception{
    List<Map<String, String>> errors ;
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(List<Map<String, String>> errors) {
        this.errors = errors;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }
}