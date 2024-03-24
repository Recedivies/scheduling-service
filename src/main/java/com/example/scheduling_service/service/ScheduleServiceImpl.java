package com.example.scheduling_service.service;

import com.example.scheduling_service.exception.JobDoesNotExistException;
import com.example.scheduling_service.model.Job;
import com.example.scheduling_service.model.TaskExecutionHistory;
import com.example.scheduling_service.model.TaskExecutionStatus;
import com.example.scheduling_service.producer.TaskProducer;
import com.example.scheduling_service.repository.JobRepository;
import com.example.scheduling_service.repository.TaskExecutionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TaskProducer taskProducer;

    private final JobRepository jobRepository;
    private final TaskExecutionHistoryRepository taskExecutionHistoryRepository;
    private final String TASK_SCHEDULE_ZSET_KEY = "task_schedule_z_set";

    @Override
    public Job findById(String id) {
        return this.jobRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new JobDoesNotExistException(id));
    }

    private Long getZSetSize() {
        Long size = redisTemplate.opsForZSet().zCard(TASK_SCHEDULE_ZSET_KEY);
        return size == null ? 0L : size;
    }

    @Bean
    @Scheduled(fixedDelay = 1000 * 60)
    public void pollingTaskSchedule() {
        while (getZSetSize() > 0) {
            Set<ZSetOperations.TypedTuple<String>> s = redisTemplate.opsForZSet().rangeWithScores(TASK_SCHEDULE_ZSET_KEY, 0L, 0L);
            assert s != null;
            ZSetOperations.TypedTuple<String> task = s.stream().findFirst().orElse(null);

            double scoreDouble = task.getScore();
            long nextExecutionTime = (long) scoreDouble;
            String jobId = task.getValue();

            Instant instant = Instant.now();

            long currentExecutionTime = instant.getEpochSecond() / 60;
            System.out.println(currentExecutionTime + " " + nextExecutionTime + " " + jobId);

            if (currentExecutionTime < nextExecutionTime) {
                break;
            }

            Job job = this.findById(jobId);
            System.out.println(job);

            taskProducer.processTaskSchedule(String.valueOf(job.getId()));
            updateTaskExecutionHistory(job);

            if (job.isRecurring()) {
                nextExecutionTime = LocalDateTime.now()
                        .plus(Duration.parse(job.getInterval()))
                        .atZone(ZoneOffset.systemDefault())
                        .toInstant()
                        .getEpochSecond() / 60;
                redisTemplate.opsForZSet().popMin(TASK_SCHEDULE_ZSET_KEY);
                redisTemplate.opsForZSet().add(TASK_SCHEDULE_ZSET_KEY, String.valueOf(job.getId()), nextExecutionTime);
            }
        }
    }

    private void updateTaskExecutionHistory(Job job) {
        TaskExecutionHistory taskExecutionHistory = TaskExecutionHistory.builder()
                .status(TaskExecutionStatus.SCHEDULED)
                .retryCount(1)
                .jobId(job.getId())
                .userId(job.getUserId())
                .build();
        System.out.println("HEHE " + taskExecutionHistory);
        this.taskExecutionHistoryRepository.save(taskExecutionHistory);
    }
}
