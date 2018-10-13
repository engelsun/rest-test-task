package com.engelsun.resttesttask.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PARTICIPANTS_TASKS",
            joinColumns = @JoinColumn(name = "PARTICIPANT_ID"),
            inverseJoinColumns = @JoinColumn(name = "TASK_ID"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Participant> tasks = new ArrayList<>();
}
