package com.codeaim.statuswarden.api.controller;

import com.codeaim.statuswarden.common.calculation.MonitorAverageResponseTime;
import com.codeaim.statuswarden.common.exception.NotFoundException;
import com.codeaim.statuswarden.common.model.Monitor;
import com.codeaim.statuswarden.common.repository.MonitorEventRepository;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/monitor")
public class MonitorController extends AbstractController
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
}
