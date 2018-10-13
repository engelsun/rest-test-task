package com.engelsun.resttesttask.repository;

import com.engelsun.resttesttask.entity.Participant;
import com.engelsun.resttesttask.entity.Task;

import java.time.LocalDate;

class TaskUtil {

    static Task initTask() {
        Task task = new Task();
        task.setName("Another Task");
        task.setBeginDate(LocalDate.of(2018, 7, 23));
        task.setEndDate(LocalDate.of(2018, 7, 24));
        return task;
    }

    static void addParticipantsToTask(Task task) {
        Participant p1 = new Participant();
        p1.setId(1);
        p1.setName("Светлана");
        Participant p2 = new Participant();
        p2.setId(3);
        p2.setName("Андрей");
        task.getParticipants().add(p1);
        task.getParticipants().add(p2);
    }
}
