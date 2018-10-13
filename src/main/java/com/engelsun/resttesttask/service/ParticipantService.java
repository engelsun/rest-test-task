package com.engelsun.resttesttask.service;

import com.engelsun.resttesttask.entity.Participant;
import com.engelsun.resttesttask.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    public List<Participant> findAll() {
        return participantRepository.findAll();
    }
}
