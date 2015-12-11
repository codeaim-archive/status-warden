package com.codeaim.statuswarden.web.api.controller;

import com.codeaim.statuswarden.common.model.Monitor;
import com.codeaim.statuswarden.common.repository.MonitorEventRepository;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import com.codeaim.statuswarden.common.calculation.MonitorAverageResponseTime;
import com.codeaim.statuswarden.common.calculation.MonitorDetail;
import com.codeaim.statuswarden.common.calculation.MonitorUptime;
import com.codeaim.statuswarden.common.exception.NotFoundException;
import com.codeaim.statuswarden.common.model.MonitorEvent;
import com.codeaim.statuswarden.common.model.Status;
import com.google.common.collect.ImmutableList;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/monitor")
public class MonitorApiController extends AbstractApiController
{
    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private MonitorEventRepository monitorEventRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Monitor> findByUserId(
        @RequestParam(value = "user-id", required = true) String userId)
    {
        return monitorRepository.findByUserId(userId);
    }

    @RequestMapping("/detail")
    public List<MonitorDetail> findByUserIdCalculateMonitorDetails(
        @RequestParam(value = "user-id", required = true) String userId,
        @RequestParam(value = "start-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(value = "end-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime)
    {
        List<MonitorDetail> monitorDetails = new ArrayList<>();

        List<Monitor> monitors = monitorRepository.findByUserId(userId);

        float totalMilliseconds = startDateTime.until(endDateTime, ChronoUnit.MILLIS);

        final List<MonitorEvent> allMonitorEvents = monitorEventRepository
            .findByMonitorIdsChangedBetweenDateTimes(
                monitors.stream().map(Monitor::getId).collect(Collectors.toList()),
                startDateTime,
                endDateTime,
                new Sort(Sort.Direction.ASC, "created"));

        final List<MonitorAverageResponseTime> averageResponseTimes = monitorEventRepository
            .findByMonitorIdsCalculateAverageResponseTimeBetweenDateTimes(
                monitors.stream().map(Monitor::getId).collect(Collectors.toList()),
                startDateTime,
                endDateTime);

        for (Monitor monitor :monitors)
        {
            Optional<MonitorAverageResponseTime> averageResponseTime = averageResponseTimes.stream().filter(x -> Objects.equals(x.getMonitorId(), monitor.getId())).findFirst();
            Optional<MonitorEvent> monitorEventBefore;
            float upMilliseconds = 0;
            List<MonitorEvent> monitorEvents = allMonitorEvents.stream().filter(x -> Objects.equals(x.getMonitorId(), monitor.getId())).collect(Collectors.toList());

            if (monitorEvents.size() > 0 && monitorEvents.get(0).getPrevious() != null)
            {
                monitorEventBefore = monitorEventRepository.findById(monitorEvents.get(0).getPrevious());
            } else
            {
                final Page<MonitorEvent> byMonitorIdBeforeDateTime = monitorEventRepository.findByMonitorIdBeforeDateTime(monitor.getId(), startDateTime, new PageRequest(0, 1));
                monitorEventBefore = byMonitorIdBeforeDateTime.hasContent() ? Optional.of(byMonitorIdBeforeDateTime.getContent().get(0)) : Optional.empty();
            }

            if (!monitorEventBefore.isPresent() || ((monitorEventBefore.get().getStatus() == Status.UP || monitorEventBefore.get().getStatus() == Status.UNKNOWN)))
            {
                upMilliseconds += startDateTime.until(monitorEvents.size() > 0 ? monitorEvents.get(0).getCreated() : endDateTime, ChronoUnit.MILLIS);
            }

            if (monitorEvents.size() > 0)
            {
                for (Pair<MonitorEvent, MonitorEvent> monitorEventPair : IntStream.range(1, monitorEvents.size()).mapToObj(i -> new Pair<>(monitorEvents.get(i - 1), monitorEvents.get(i))).collect(Collectors.toList()))
                {
                    upMilliseconds += monitorEventPair.getKey().getStatus() == Status.UP ?
                        monitorEventPair.getKey().getCreated().until(monitorEventPair.getValue().getCreated(), ChronoUnit.MILLIS) : 0;
                }

                upMilliseconds += monitorEvents.get(monitorEvents.size() - 1).getStatus() == Status.UP ?
                    monitorEvents.get(monitorEvents.size() - 1).getCreated().until(endDateTime, ChronoUnit.MILLIS) : 0;
            }

            monitorDetails.add(MonitorDetail
                .builder()
                .id(monitor.getId())
                .monitorEventId(monitor.getMonitorEventId())
                .userId(monitor.getUserId())
                .name(monitor.getName())
                .url(monitor.getUrl())
                .state(monitor.getState())
                .status(monitor.getStatus())
                .scheduler(monitor.getScheduler())
                .created(monitor.getCreated())
                .audit(monitor.getAudit())
                .locked(monitor.getLocked())
                .interval(monitor.getInterval())
                .version(monitor.getVersion())
                .confirming(monitor.isConfirming())
                .uptime((upMilliseconds / totalMilliseconds) * 100)
                .averageResponseTime(averageResponseTime.get().getAverageResponseTime())
                .build());
        }

        return monitorDetails;
    }

    @RequestMapping("/{monitor-id}")
    public Monitor findById(
        @PathVariable(value = "monitor-id") String monitorId)
    {
        return monitorRepository.findOne(monitorId);
    }

    @RequestMapping("/{monitor-id}/response-time")
    public List<MonitorAverageResponseTime> findByIdCalculateAverageResponseTimeBetweenDateTimes(
        @PathVariable(value = "monitor-id") String monitorId,
        @RequestParam(value = "start-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(value = "end-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime)
    {
        final Monitor monitor = monitorRepository.findOne(monitorId);

        if (monitor != null)
            return monitorEventRepository
                .findByMonitorIdsCalculateAverageResponseTimeBetweenDateTimes(
                    ImmutableList
                        .<String>builder()
                        .add(monitor.getId())
                        .build()
                    ,
                    startDateTime,
                    endDateTime);
        else
            throw new NotFoundException();
    }

    @RequestMapping("/{monitor-id}/uptime")
    public List<MonitorUptime> findByIdCalculateUptimeBetweenDateTimes(
        @PathVariable(value = "monitor-id") String monitorId,
        @RequestParam(value = "start-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(value = "end-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime)
    {
        final Monitor monitor = monitorRepository.findOne(monitorId);

        if (monitor != null)
        {
            float totalMilliseconds = startDateTime.until(endDateTime, ChronoUnit.MILLIS);
            float upMilliseconds = 0;

            final List<MonitorEvent> monitorEvents = monitorEventRepository
                .findByMonitorIdsChangedBetweenDateTimes(
                    ImmutableList
                        .<String>builder()
                        .add(monitor.getId())
                        .build(),
                    startDateTime,
                    endDateTime,
                    new Sort(Sort.Direction.ASC, "created"));

            Optional<MonitorEvent> monitorEventBefore;

            if (monitorEvents.size() > 0 && monitorEvents.get(0).getPrevious() != null)
            {
                monitorEventBefore = monitorEventRepository.findById(monitorEvents.get(0).getPrevious());
            } else
            {
                final Page<MonitorEvent> byMonitorIdBeforeDateTime = monitorEventRepository.findByMonitorIdBeforeDateTime(monitor.getId(), startDateTime, new PageRequest(0, 1));
                monitorEventBefore = byMonitorIdBeforeDateTime.hasContent() ? Optional.of(byMonitorIdBeforeDateTime.getContent().get(0)) : Optional.empty();
            }

            if (!monitorEventBefore.isPresent() || ((monitorEventBefore.get().getStatus() == Status.UP || monitorEventBefore.get().getStatus() == Status.UNKNOWN)))
            {
                upMilliseconds += startDateTime.until(monitorEvents.size() > 0 ? monitorEvents.get(0).getCreated() : endDateTime, ChronoUnit.MILLIS);
            }

            if (monitorEvents.size() > 0)
            {
                for (Pair<MonitorEvent, MonitorEvent> monitorEventPair : IntStream.range(1, monitorEvents.size()).mapToObj(i -> new Pair<>(monitorEvents.get(i - 1), monitorEvents.get(i))).collect(Collectors.toList()))
                {
                    upMilliseconds += monitorEventPair.getKey().getStatus() == Status.UP ?
                        monitorEventPair.getKey().getCreated().until(monitorEventPair.getValue().getCreated(), ChronoUnit.MILLIS) : 0;
                }

                upMilliseconds += monitorEvents.get(monitorEvents.size() - 1).getStatus() == Status.UP ?
                    monitorEvents.get(monitorEvents.size() - 1).getCreated().until(endDateTime, ChronoUnit.MILLIS) : 0;
            }

            return Collections.singletonList(
                MonitorUptime
                    .builder()
                    .monitorId(monitorId)
                    .uptime((upMilliseconds / totalMilliseconds) * 100)
                    .build());
        } else
            throw new NotFoundException();
    }
}
