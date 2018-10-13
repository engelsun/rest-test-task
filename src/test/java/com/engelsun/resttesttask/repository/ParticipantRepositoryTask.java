package com.engelsun.resttesttask.repository;

import com.engelsun.resttesttask.entity.Participant;
import com.engelsun.resttesttask.entity.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class ParticipantRepositoryTask {
    @Autowired
    ParticipantRepository participantRepository;

    @Autowired
    TaskRepository taskRepository;

    Task task;

    @Before
    public void initTask() {
        task = TaskUtil.initTask();
    }

    private void addParticipantsToTask() {
        TaskUtil.addParticipantsToTask(task);
    }

    @Test
    public void findAll() {
        List<Participant> participants = participantRepository.findAll();
        assertEquals(5, participants.size());
    }

    @Test
    public void findAllAfterSaveTaskWithParticipants() {
        addParticipantsToTask();
        taskRepository.save(task);
        assertEquals(3, taskRepository.findAll().size());

        List<Participant> participants = participantRepository.findAll();
        assertEquals(5, participants.size());
    }

    @Test
    public void findAllAfterUpdateTaskWithParticipants() {
        addParticipantsToTask();
        Task savedTask = taskRepository.save(task);
        assertEquals(3, taskRepository.findAll().size());

        savedTask.getParticipants().clear();
        taskRepository.save(savedTask);
        assertEquals(3, taskRepository.findAll().size());
        assertEquals(0, taskRepository.findOne(savedTask.getId()).getParticipants().size());

        List<Participant> participants = participantRepository.findAll();
        assertEquals(5, participants.size());
    }

    @Test
    public void findAllAfterDeleteTaskWithParticipants() {
        addParticipantsToTask();
        Task savedTask = taskRepository.save(task);
        assertEquals(3, taskRepository.findAll().size());

        taskRepository.delete(savedTask);
        assertEquals(2, taskRepository.findAll().size());

        List<Participant> participants = participantRepository.findAll();
        assertEquals(5, participants.size());
    }
}
