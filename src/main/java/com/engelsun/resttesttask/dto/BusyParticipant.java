package com.engelsun.resttesttask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
@Getter @Setter
@AllArgsConstructor
@ToString
public class BusyParticipant implements Comparable<BusyParticipant> {
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate busyFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate busyTo;

    @Override
    public int compareTo(BusyParticipant o) {
        return this.name.compareTo(o.getName());
    }
}
