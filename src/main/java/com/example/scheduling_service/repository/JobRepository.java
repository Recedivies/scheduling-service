package com.example.scheduling_service.repository;

import com.example.scheduling_service.model.Job;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {
    @NonNull Optional<Job> findById(@NonNull UUID id);
}
