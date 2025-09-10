package org.example.currencyexchangeweb.exeptions;

public class ValidatorException extends RuntimeException {
    public ValidatorException(String message) {
        super(message);
    }
}
