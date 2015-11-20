package com.codeaim.statuswarden.api.controller;

import com.codeaim.statuswarden.common.model.MonitorEvent;
import com.codeaim.statuswarden.common.repository.MonitorEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/monitor-event")
public class MonitorEventController extends AbstractController
{
    @Autowired
    private MonitorEventRepository monitorEventRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<MonitorEvent> findByMonitorIdBetweenDateTimes(
        @RequestParam(value = "monitor-id", required = true) String monitorId,
        @RequestParam(value = "start-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
        @RequestParam(value = "end-date-time", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime)
    {
        return monitorEventRepository
            .findByMonitorIdBetweenDateTimes(
                monitorId,
                startDateTime,
                endDateTime, new Sort(Sort.Direction.ASC, "created"));
    }

    @RequestMapping("/{monitor-event-id}")
    public MonitorEvent findById(
        @PathVariable(value = "monitor-event-id") String monitorEventId)
    {
        return monitorEventRepository.findOne(monitorEventId);
    }
}
