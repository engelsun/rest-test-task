package com.engelsun.resttesttask.repository;

import com.engelsun.resttesttask.TaskUtil;
import com.engelsun.resttesttask.entity.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class TaskRepositoryTest {
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
        List<Task> tasks = taskRepository.findAll();
        assertEquals(2, tasks.size());
    }

    @Test
    public void findOneWithoutParticipants() {
        Task savedTask = taskRepository.save(task);
        Task foundTask = taskRepository.findOne(savedTask.getId());

        assertEquals(savedTask, foundTask);
    }

    @Test
    public void findOneWithParticipants() {
        addParticipantsToTask();

        Task savedTask = taskRepository.save(task);

        Task foundTask = taskRepository.findOne(savedTask.getId());

        assertEquals(savedTask, foundTask);
        assertEquals(2, foundTask.getParticipants().size());
    }

    @Test
    public void saveWithoutParticipants() {
        Task savedTask = taskRepository.save(task);

        assertEquals(savedTask, taskRepository.findOne(savedTask.getId()));
        assertEquals(3, taskRepository.findAll().size());
    }

    @Test
    public void saveWithParticipants() {
        addParticipantsToTask();

        Task savedTask = taskRepository.save(task);
        int id = savedTask.getId();

        assertEquals(savedTask, taskRepository.findOne(id));
        assertEquals(3, taskRepository.findAll().size());
        assertEquals(2, savedTask.getParticipants().size());
        assertEquals(1, savedTask.getParticipants().get(0).getId());
        assertEquals(3, savedTask.getParticipants().get(1).getId());
    }

    @Test
    public void deleteWithoutParticipants() {
        Task savedTask = taskRepository.save(task);

        taskRepository.delete(savedTask.getId());

        assertEquals(2, taskRepository.findAll().size());
    }

    @Test
    public void deleteWithParticipants() {
        addParticipantsToTask();

        Task savedTask = taskRepository.save(task);

        taskRepository.delete(savedTask.getId());

        assertEquals(2, taskRepository.findAll().size());
    }

    @Test
    public void updateWithoutParticipants() {
        Task savedTask = taskRepository.save(task);

        savedTask.setName("Updated Task");
        addParticipantsToTask();
        savedTask.setParticipants(task.getParticipants());

        Task updatedTask = taskRepository.save(savedTask);

        assertEquals(3, taskRepository.findAll().size());
        assertEquals(savedTask.getId(), updatedTask.getId());
        assertEquals(2, updatedTask.getParticipants().size());
        assertEquals(1, updatedTask.getParticipants().get(0).getId());
        assertEquals(3, updatedTask.getParticipants().get(1).getId());
    }

    @Test
    public void updateWithParticipants() {
        addParticipantsToTask();

        Task savedTask = taskRepository.save(task);
        savedTask.getParticipants().clear();

        Task updatedTask = taskRepository.save(savedTask);

        assertEquals(3, taskRepository.findAll().size());
        assertEquals(savedTask.getId(), updatedTask.getId());
        assertEquals(0, updatedTask.getParticipants().size());
    }
}
