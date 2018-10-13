package com.engelsun.resttesttask.service;

import com.engelsun.resttesttask.TaskUtil;
import com.engelsun.resttesttask.entity.Task;
import com.engelsun.resttesttask.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

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

    private void addParticipantsToTask() {
        TaskUtil.addParticipantsToTask(task);
    }

    @Test
    public void findOne() {
        Task task = taskService.findOne(2);

        assertEquals(2, task.getId());
    }

    @Test
    public void findAll() {
        int tasksCount = taskService.findAll().size();

        assertEquals(2, tasksCount);
    }

    @Test
    public void add() {
        taskService.add(task);
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
