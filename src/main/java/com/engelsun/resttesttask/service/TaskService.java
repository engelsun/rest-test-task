package com.engelsun.resttesttask.service;

import com.engelsun.resttesttask.entity.Task;
import com.engelsun.resttesttask.exception.IllegalSelectedParticipantsException;
import com.engelsun.resttesttask.repository.TaskRepository;
import com.engelsun.resttesttask.util.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            Map<String, Period> illegalSelectedParticipantNames = checkForOverlappedPeriods(task);

            if (illegalSelectedParticipantNames.size() > 0) {
                processException(illegalSelectedParticipantNames);
            } else {
                taskRepository.save(task);
            }
        }
    }

    private Map<String, Period> checkForOverlappedPeriods(Task task) {
        List<Task> filteredTasks = taskRepository.findAllByOverlappedPeriods(task.getBeginDate(), task.getEndDate());
        Map<String, Period> illegalSelectedParticipantNames = new HashMap<>();

        filteredTasks.forEach(t -> {
            t.getParticipants().forEach(p -> {
                if (task.getParticipants().contains(p)) {
                    illegalSelectedParticipantNames.put(p.getName(),
                            new Period(t.getBeginDate(), t.getEndDate()));
                }
            });
        });

        return illegalSelectedParticipantNames;
    }

    private void processException(Map<String, Period> illegalSelectedParticipantNames) {
        StringBuilder sb = new StringBuilder();
        String in = " in ";
        sb.append("\nSelected participants are working on others tasks in this period, please choose another period");
        illegalSelectedParticipantNames.entrySet().forEach(entry -> {
            Period p = entry.getValue();
            sb.append("\n" + entry.getKey() + in + p.toString());
        });
        throw new IllegalSelectedParticipantsException(sb.toString());
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
