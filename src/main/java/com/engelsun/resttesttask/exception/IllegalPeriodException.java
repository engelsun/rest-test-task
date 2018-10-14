package com.engelsun.resttesttask.exception;

public class IllegalPeriodException extends RuntimeException {
    public static final String MASSAGE = "beginDate must be before endDate";

    public IllegalPeriodException() {
        super(MASSAGE);
    }
}
