package com.strawhats.userregistrationloginsystem.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String fieldName;
    private String fieldValue;

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String fieldName, String fieldValue) {
        super(String.format("User with %s : %s already exists", fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
