package com.engelsun.resttesttask.service;

import com.engelsun.resttesttask.entity.Task;
import com.engelsun.resttesttask.exception.IllegalPeriodException;
import com.engelsun.resttesttask.exception.IllegalSelectedParticipantsException;
import com.engelsun.resttesttask.repository.TaskRepository;
import com.engelsun.resttesttask.dto.BusyParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

            throwExceptionIfInvalidPeriod(task.getBeginDate(), task.getEndDate());

            List<BusyParticipant> busyParticipants = checkOnOverlappedPeriods(task);

            if (busyParticipants.size() > 0) {
                processException(busyParticipants);
            } else {
                taskRepository.save(task);
            }
        }
    }

    private void throwExceptionIfInvalidPeriod(LocalDate beginDate, LocalDate endDate) {
        if (beginDate.isAfter(endDate)) {
            throw new IllegalPeriodException();
        }
    }

    private List<BusyParticipant> checkOnOverlappedPeriods(Task task) {
        List<Task> filteredTasks = taskRepository.findAllByOverlappedPeriods(task.getBeginDate(), task.getEndDate());
        List<BusyParticipant> busyParticipants = new ArrayList<>();

        filteredTasks.forEach(t -> {
            t.getParticipants().forEach(participant -> {
                if (task.getParticipants().contains(participant)) {
                    busyParticipants.add(
                            new BusyParticipant(participant.getName(), t.getBeginDate(), t.getEndDate()));
                }
            });
        });

        return busyParticipants;
    }

    private void processException(List<BusyParticipant> busyParticipants) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(IllegalSelectedParticipantsException.MASSAGE);
        Collections.sort(busyParticipants);
        busyParticipants.forEach(participant -> {
            sb.append("\n");
            sb.append(participant.getName());
            sb.append(" busyFrom ");
            sb.append(participant.getBusyFrom());
            sb.append(" busyTo ");
            sb.append(participant.getBusyTo());
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
