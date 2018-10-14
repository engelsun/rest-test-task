package com.engelsun.resttesttask.repository;

import com.engelsun.resttesttask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <Task, Integer> {
    @Query("select t from Task t where t.beginDate <= ?2 and t.endDate >= ?1")
    List<Task> findAllByOverlappedPeriods(LocalDate beginDate, LocalDate endDate);
}
