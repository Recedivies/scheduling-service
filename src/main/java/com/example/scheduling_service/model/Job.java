package com.example.scheduling_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job")
public class Job {
    @Id
    private UUID id;

    private boolean isRecurring;

    private String interval;

    private int maxRetryCount;

    private String jobType;

    private String config;

    private UUID userId;

    @Column(name = "callback_url")
    private String callbackURL;
}
