package com.engelsun.resttesttask.service;

import com.engelsun.resttesttask.TaskUtil;
import com.engelsun.resttesttask.entity.Participant;
import com.engelsun.resttesttask.entity.Task;
import com.engelsun.resttesttask.exception.IllegalPeriodException;
import com.engelsun.resttesttask.exception.IllegalSelectedParticipantsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    @Autowired
    TaskService taskService;

    Task task;

    @Before
    public void initTask() {
        task = TaskUtil.initTask();
    }

    private Task initTaskWithCustomPeriodAndParticipants(LocalDate beginDate, LocalDate endDate) {
        task = TaskUtil.initTaskWithCustomPeriod(beginDate, endDate);
        TaskUtil.addParticipantsToTask(task);
        return task;
    }

    private void processExceptionIfThrownOrAdd(String ...names) {
        try {
            taskService.add(task);
        } catch (IllegalPeriodException ex) {
            System.out.println(ex.getMessage());
            assertTrue(ex.getMessage().equals(IllegalPeriodException.MASSAGE));
        } catch (IllegalSelectedParticipantsException ex) {
            System.out.println(ex.getMessage());
            assertTrue(ex.getMessage().contains("Светлана"));
            assertTrue(ex.getMessage().contains("Андрей"));
            if (names.length > 0) {
                Arrays.asList(names)
                        .forEach(name -> assertTrue(ex.getMessage().contains(name)));
            }
        }
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void findOne() {
        Task task = taskService.findOne(2);

        assertEquals(2, task.getId());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void findAll() {
        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add() {
        taskService.add(task);
        int tasksCount = taskService.findAll().size();

        assertEquals(3, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addWhenBeginDateAfterEndDate() {
        task = initTaskWithCustomPeriodAndParticipants(
                LocalDate.of(2018, 8, 1),
                LocalDate.of(2018, 7, 10));

        processExceptionIfThrownOrAdd();

        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addWithParticipantsWithInsidePeriod() {
        task = initTaskWithCustomPeriodAndParticipants(
                LocalDate.of(2018, 7, 1),
                LocalDate.of(2018, 7, 10));

        processExceptionIfThrownOrAdd();

        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addWithParticipantsWithStartOutsidePeriod() {
        task = initTaskWithCustomPeriodAndParticipants(
                LocalDate.of(2018, 6, 1),
                LocalDate.of(2018, 7, 10));

        processExceptionIfThrownOrAdd();

        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addWithParticipantsWithEndOutsidePeriod() {
        task = initTaskWithCustomPeriodAndParticipants(
                LocalDate.of(2018, 7, 1),
                LocalDate.of(2018, 8, 10));

        processExceptionIfThrownOrAdd();

        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addWithParticipantsWithOutsidePeriod() {
        task = initTaskWithCustomPeriodAndParticipants(
                LocalDate.of(2018, 6, 1),
                LocalDate.of(2018, 8, 10));

        processExceptionIfThrownOrAdd();

        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addWithParticipantsWithOutsidePeriodOverlappedTwoPeriods() {
        task = initTaskWithCustomPeriodAndParticipants(
                LocalDate.of(2018, 6, 1),
                LocalDate.of(2018, 8, 10));

        Participant participant = new Participant();
        participant.setId(5);
        participant.setName("Владимир");
        task.getParticipants().add(participant);

        processExceptionIfThrownOrAdd("Владимир");

        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addWithParticipantsWithoutOverlapped() {
        task = initTaskWithCustomPeriodAndParticipants(
                LocalDate.of(2018, 6, 1),
                LocalDate.of(2018, 6, 1));

        processExceptionIfThrownOrAdd();

        int tasksCount = taskService.findAll().size();

        assertEquals(3, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete() {
        Task task = taskService.findOne(1);
        taskService.delete(task.getId());

        int tasksCount = taskService.findAll().size();

        assertEquals(1, tasksCount);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update() {
        Task task = taskService.findOne(2);
        task.getParticipants().clear();
        taskService.update(task);

        Task updatedTask = taskService.findOne(2);
        int taskParticipantsSize = updatedTask.getParticipants().size();

        assertEquals(0, taskParticipantsSize);
    }
}
