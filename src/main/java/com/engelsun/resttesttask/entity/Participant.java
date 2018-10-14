package com.engelsun.resttesttask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "participants")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();
}
