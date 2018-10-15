package com.engelsun.resttesttask.handler;

import com.engelsun.resttesttask.exception.IllegalPeriodException;
import com.engelsun.resttesttask.exception.IllegalSelectedParticipantsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler(IllegalSelectedParticipantsException.class)
    protected ResponseEntity<Object> handleIllegalSelectedParticipants(
            IllegalSelectedParticipantsException ex) {

        ExceptionBody exceptionBody = new ExceptionBody(HttpStatus.BAD_REQUEST);

        int from = ex.getMessage().indexOf("The");
        int to = ex.getMessage().lastIndexOf("period");
        String message = ex.getMessage().substring(from, to + 6);

        exceptionBody.setMessage(message);
        exceptionBody.setMessageDetails(ex.getBusyParticipants());
        return buildResponseEntity(exceptionBody);
    }

    @ExceptionHandler(IllegalPeriodException.class)
    protected ResponseEntity<Object> handleIllegalPeriod(IllegalPeriodException ex) {

        ExceptionBody exceptionBody = new ExceptionBody(HttpStatus.BAD_REQUEST);
        exceptionBody.setMessage(ex.getMessage());

        return buildResponseEntity(exceptionBody);
    }

    private ResponseEntity<Object> buildResponseEntity(ExceptionBody exceptionBody) {
        return new ResponseEntity<>(exceptionBody, exceptionBody.getStatus());
    }
}
