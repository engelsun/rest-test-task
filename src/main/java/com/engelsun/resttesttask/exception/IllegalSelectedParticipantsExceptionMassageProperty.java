package com.engelsun.resttesttask.exception;

import lombok.*;

import java.time.LocalDate;
@Getter @Setter
@AllArgsConstructor
@ToString
public class IllegalSelectedParticipantsExceptionMassageProperty {
    private LocalDate taskBeginDate;
    private LocalDate taskEndDate;
    private String participantName;
}
