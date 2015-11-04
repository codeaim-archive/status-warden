package com.codeaim.statuswarden.scheduler;

import com.codeaim.statuswarden.common.repository.MonitorEventRepository;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import com.codeaim.statuswarden.scheduler.task.StatusAcquisitionTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TaskSchedule
{
    private static final Logger log = LoggerFactory.getLogger(TaskSchedule.class);

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private MonitorEventRepository monitorEventRepository;

    @Value("${com.codeaim.statuswarden.scheduler.name}")
    private String schedulerName;

    @Value("${com.codeaim.statuswarden.scheduler.isClustered}")
    private boolean isClustered;

    @Scheduled(fixedRate = 1000)
    public void StatusAcquisitionTask()
    {
        log.info("Starting StatusAcquisitionTask {}", LocalDateTime.now(ZoneOffset.UTC));
        new StatusAcquisitionTask(monitorRepository, monitorEventRepository, isClustered, schedulerName);
    }
}
