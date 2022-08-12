package io.github.zam0k.HolyHealth.exception;

public class SearchHealthProblemsException extends RuntimeException{
    public SearchHealthProblemsException() {
        super("One or more health problems cannot be found.");
    }
}
