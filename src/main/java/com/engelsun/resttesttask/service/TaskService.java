package com.engelsun.resttesttask.service;

import com.engelsun.resttesttask.entity.Task;
import com.engelsun.resttesttask.exception.IllegalPeriodException;
import com.engelsun.resttesttask.exception.IllegalSelectedParticipantsException;
import com.engelsun.resttesttask.repository.TaskRepository;
import com.engelsun.resttesttask.exception.IllegalSelectedParticipantsExceptionMassageProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

            List<IllegalSelectedParticipantsExceptionMassageProperty> exceptionMassageProperties = checkOnOverlappedPeriods(task);

            if (exceptionMassageProperties.size() > 0) {
                processException(exceptionMassageProperties);
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

    private List<IllegalSelectedParticipantsExceptionMassageProperty> checkOnOverlappedPeriods(Task task) {
        List<Task> filteredTasks = taskRepository.findAllByOverlappedPeriods(task.getBeginDate(), task.getEndDate());
        List<IllegalSelectedParticipantsExceptionMassageProperty> illegalSelectedParticipantNames = new ArrayList<>();

        filteredTasks.forEach(t -> {
            t.getParticipants().forEach(p -> {
                if (task.getParticipants().contains(p)) {
                    illegalSelectedParticipantNames.add(
                            new IllegalSelectedParticipantsExceptionMassageProperty(t.getBeginDate(), t.getEndDate(), p.getName()));
                }
            });
        });

        return illegalSelectedParticipantNames;
    }

    private void processException(List<IllegalSelectedParticipantsExceptionMassageProperty> exceptionMessageProperties) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nThe selected participants are working on others tasks during this period, please choose another period");
        exceptionMessageProperties.forEach(massage -> {
            sb.append("\n");
            sb.append(massage.getParticipantName());
            sb.append(" from ");
            sb.append(massage.getTaskBeginDate());
            sb.append(" to ");
            sb.append(massage.getTaskEndDate());
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
