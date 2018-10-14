package com.engelsun.resttesttask.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter @Setter
@AllArgsConstructor
@ToString
public class Period {
    private LocalDate beginDate;
    private LocalDate endDate;
}
