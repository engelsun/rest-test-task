package com.engelsun.resttesttask.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
class ExceptionBody {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private List<?> messageDetails;

    private ExceptionBody() {
        timestamp = LocalDateTime.now();
    }

    ExceptionBody(HttpStatus status) {
        this();
        this.status = status;
    }
}
