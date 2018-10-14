package com.engelsun.resttesttask.controller;

import com.engelsun.resttesttask.entity.Participant;
import com.engelsun.resttesttask.entity.Task;
import com.engelsun.resttesttask.service.ParticipantService;
import com.engelsun.resttesttask.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ParticipantService participantService;

    @GetMapping()
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task getOne(@PathVariable("id") int id) {
        return taskService.findOne(id);
    }


    @PostMapping("/new")
    public void add(@RequestBody Task task) {
        taskService.add(task);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Task task) {
        taskService.update(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        taskService.delete(id);
    }

    @GetMapping("/participants")
    public List<Participant> getAvailableParticipants() {
        return participantService.findAll();
    }
}
