package com.example.scheduling_service.service;

import com.example.scheduling_service.model.Job;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {
    Job findById(String id);
}
