package com.codeaim.statuswarden.scheduler.task;


import com.codeaim.statuswarden.common.model.Monitor;
import com.codeaim.statuswarden.common.model.MonitorEvent;
import com.codeaim.statuswarden.common.model.State;
import com.codeaim.statuswarden.common.model.Status;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Objects;

public class StatusAcquisitionTask
{
    private static final Logger log = LoggerFactory.getLogger(StatusAcquisitionTask.class);

    public StatusAcquisitionTask(MonitorRepository monitorRepository, boolean isClustered, String schedulerName)
    {
        getElectableMonitors(monitorRepository, isClustered, schedulerName)
            .getContent()
            .stream()
            .map(monitor -> markMonitorElected(monitor, monitorRepository, schedulerName))
            .map(monitor -> checkAndUpdateMonitor(monitor, monitorRepository))
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
        monitor.setState(State.ELECTED);
        monitor.setLocked(new Date((now().getTime() + (60 * 1000))));
        monitor.setScheduler(schedulerName);
        return monitorRepository.save(monitor);
    }

    private Monitor checkAndUpdateMonitor(Monitor monitor, MonitorRepository monitorRepository)
    {
        MonitorEvent monitorEvent = getMonitorCheckEvent(monitor);
        monitor.getEvents().add(monitorEvent);

        if (!monitorEvent.isChanged() && !monitorEvent.isConfirmation())
            statusChangeNone(monitor);
        if (monitorEvent.isChanged() && !monitorEvent.isConfirmation())
            statusChangeConfirm(monitor);
        if (!monitorEvent.isChanged() && monitorEvent.isConfirmation())
            statusChangeConfirmationInconclusive(monitor);
        if (monitorEvent.isChanged() && monitorEvent.isConfirmation())
            statusChangeConfirmed(monitor, monitorEvent);

        return updateMonitor(monitor, monitorRepository);
    }

    private MonitorEvent getMonitorCheckEvent(Monitor monitor)
    {
        log.info("Getting monitor event {}", monitor);
        MonitorEvent monitorEvent = requestUrlAndCreateMonitorEvent(monitor);
        log.info("Received monitor event {}", monitorEvent);
        return monitorEvent;
    }

    public MonitorEvent requestUrlAndCreateMonitorEvent(Monitor monitor)
    {
        try
        {
            long startResponseTime = System.currentTimeMillis();
            int statusCode = HttpClients
                .createDefault()
                .execute(new HttpHead(monitor.getUrl()))
                .getStatusLine()
                .getStatusCode();
            return MonitorEvent
                .builder()
                .scheduler(monitor.getScheduler())
                .responseTime(System.currentTimeMillis() - startResponseTime)
                .statusCode(statusCode)
                .status((statusCode >= 200 && statusCode <= 399) ? Status.UP : Status.DOWN)
                .changed(!Objects.equals((statusCode >= 200 && statusCode <= 399) ? Status.UP : Status.DOWN, monitor.getStatus()))
                .confirmation(monitor.isConfirming())
                .build();
        } catch (Exception exception)
        {
            return MonitorEvent
                .builder()
                .status(Status.ERROR)
                .build();
        }
    }

    private void statusChangeNone(Monitor monitor)
    {
        markMonitorUpdated(monitor);
    }

    private void statusChangeConfirm(Monitor monitor)
    {
        monitor.setConfirming(true);
    }

    private void statusChangeConfirmationInconclusive(Monitor monitor)
    {
        monitor.setConfirming(false);
    }

    private void statusChangeConfirmed(Monitor monitor, MonitorEvent monitorEvent)
    {
        monitor.setStatus(monitorEvent.getStatus());
        monitor.setConfirming(false);
        markMonitorUpdated(monitor);
    }

    private void markMonitorUpdated(Monitor monitor)
    {
        monitor.setUpdated(now());
        monitor.setAudit(new Date(now().getTime() + (monitor.getInterval() * 60 * 1000)));
    }

    private Monitor updateMonitor(Monitor monitor, MonitorRepository monitorRepository)
    {
        log.info("Updating monitor {}", monitor);
        monitor.setState(State.WAITING);
        monitor.setLocked(null);
        return monitorRepository.save(monitor);
    }

    private Date now()
    {
        return new Date();
    }
}
