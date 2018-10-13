package com.engelsun.resttesttask.service;

import com.engelsun.resttesttask.entity.Task;
import com.engelsun.resttesttask.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task findOne(int id) {
        return taskRepository.findOne(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void add(Task task) {
        if (task.getId() == 0) {
            taskRepository.save(task);
        }
    }

    public void delete(int id) {
        taskRepository.delete(id);
    }

    public void update(Task task) {
        if (taskRepository.exists(task.getId())) {
            taskRepository.save(task);
        }
    }
}
