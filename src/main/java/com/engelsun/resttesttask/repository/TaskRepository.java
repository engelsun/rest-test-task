package com.engelsun.resttesttask.repository;

import com.engelsun.resttesttask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository <Task, Integer> {
}
