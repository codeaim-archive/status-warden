package com.codeaim.statuswarden.scheduler.task;


import com.codeaim.statuswarden.common.model.Monitor;
import com.codeaim.statuswarden.common.model.MonitorEvent;
import com.codeaim.statuswarden.common.model.State;
import com.codeaim.statuswarden.common.model.Status;
import com.codeaim.statuswarden.common.repository.MonitorEventRepository;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class StatusAcquisitionTask
{
    private static final Logger log = LoggerFactory.getLogger(StatusAcquisitionTask.class);

    public StatusAcquisitionTask(MonitorRepository monitorRepository, MonitorEventRepository monitorEventRepository, boolean isClustered, String schedulerName)
    {
        getElectableMonitors(monitorRepository, isClustered, schedulerName)
            .getContent()
            .stream()
            .map(monitor -> markMonitorElected(monitor, monitorRepository, schedulerName))
            .map(monitor -> checkAndUpdateMonitor(monitor, monitorRepository, monitorEventRepository))
            .toArray();
    }

    private Page<Monitor> getElectableMonitors(MonitorRepository monitorRepository, boolean clustered, String scheduler)
    {
        log.info("Getting getting electable monitors");
        PageRequest pageRequest = new PageRequest(0, 5, new Sort(Sort.Direction.ASC, "audit"));

        return clustered ?
            monitorRepository.findElectableClustered(now(), scheduler, pageRequest) :
            monitorRepository.findElectable(now(), pageRequest);
    }

    private Monitor markMonitorElected(Monitor monitor, MonitorRepository monitorRepository, String schedulerName)
    {
        log.info("Marking monitor elected {}", monitor);

        return monitorRepository.save(Monitor
            .buildFrom(monitor)
            .state(State.ELECTED)
            .locked(now().plusMinutes(1))
            .scheduler(schedulerName)
            .build());
    }

    private Monitor checkAndUpdateMonitor(Monitor monitor, MonitorRepository monitorRepository, MonitorEventRepository monitorEventRepository)
    {
        MonitorEvent monitorEvent = getMonitorCheckEvent(monitor, monitorEventRepository);

        if (monitorEvent.isChanged() && monitorEvent.isConfirmation())
            return monitorRepository.save(statusChangeConfirmed(monitor, monitorEvent));
        if (!monitorEvent.isChanged() && monitorEvent.isConfirmation())
            return monitorRepository.save(statusChangeConfirmationInconclusive(monitor, monitorEvent));
        if (monitorEvent.isChanged())
            return monitorRepository.save(statusChangeConfirmationRequired(monitor, monitorEvent));

        return monitorRepository.save(statusChangeNone(monitor, monitorEvent));
    }

    private MonitorEvent getMonitorCheckEvent(Monitor monitor, MonitorEventRepository monitorEventRepository)
    {
        log.info("Getting monitor event for monitor {}", monitor);
        MonitorEvent monitorEvent = requestUrlAndCreateMonitorEvent(monitor, monitorEventRepository);
        log.info("Received monitor event {}", monitorEvent);
        return monitorEvent;
    }

    public MonitorEvent requestUrlAndCreateMonitorEvent(Monitor monitor, MonitorEventRepository monitorEventRepository)
    {
        try
        {
            long startResponseTime = System.currentTimeMillis();
            int statusCode = HttpClients
                .createDefault()
                .execute(new HttpHead(monitor.getUrl()))
                .getStatusLine()
                .getStatusCode();
            return monitorEventRepository.save(MonitorEvent
                .builder()
                .monitorId(monitor.getId())
                .previous(monitor.getMonitorEventId())
                .scheduler(monitor.getScheduler())
                .responseTime(System.currentTimeMillis() - startResponseTime)
                .statusCode(statusCode)
                .status((statusCode >= 200 && statusCode <= 399) ? Status.UP : Status.DOWN)
                .changed(!Objects.equals((statusCode >= 200 && statusCode <= 399) ? Status.UP : Status.DOWN, monitor.getStatus()))
                .confirmation(monitor.isConfirming())
                .build());
        } catch (Exception exception)
        {
            return monitorEventRepository.save(MonitorEvent
                .builder()
                .status(Status.ERROR)
                .build());
        }
    }

    private Monitor statusChangeNone(Monitor monitor, MonitorEvent monitorEvent)
    {
        log.info("Updating monitor {} - No status change", monitor);

        return Monitor
            .buildFrom(monitor)
            .monitorEventId(monitorEvent.getId())
            .audit(now().plusMinutes(monitor.getInterval()))
            .state(State.WAITING)
            .locked(null)
            .build();
    }

    private Monitor statusChangeConfirmationRequired(Monitor monitor, MonitorEvent monitorEvent)
    {
        log.info("Updating monitor {} - Status change confirmation required", monitor);

        return Monitor
            .buildFrom(monitor)
            .monitorEventId(monitorEvent.getId())
            .confirming(true)
            .state(State.WAITING)
            .locked(null)
            .build();
    }

    private Monitor statusChangeConfirmationInconclusive(Monitor monitor, MonitorEvent monitorEvent)
    {
        log.info("Updating monitor {} - Status change confirmation inconclusive", monitor);

        return Monitor
            .buildFrom(monitor)
            .monitorEventId(monitorEvent.getId())
            .confirming(false)
            .state(State.WAITING)
            .locked(null)
            .build();
    }

    private Monitor statusChangeConfirmed(Monitor monitor, MonitorEvent monitorEvent)
    {
        log.info("Updating monitor {} - Confirmed status change ", monitor);

        return Monitor
            .buildFrom(monitor)
            .monitorEventId(monitorEvent.getId())
            .status(monitorEvent.getStatus())
            .confirming(false)
            .audit(now().plusMinutes(monitor.getInterval()))
            .state(State.WAITING)
            .locked(null)
            .build();
    }

    private LocalDateTime now()
    {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
