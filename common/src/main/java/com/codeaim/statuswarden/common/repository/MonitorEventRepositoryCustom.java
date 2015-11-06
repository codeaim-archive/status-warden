package com.codeaim.statuswarden.common.repository;

import com.codeaim.statuswarden.common.calculation.MonitorAverageResponseTime;

import java.time.LocalDateTime;
import java.util.List;

public interface MonitorEventRepositoryCustom
{
    List<MonitorAverageResponseTime> findByMonitorIdsCalculateAverageResponseTimeBetweenDateTimes(
        List<String> monitorIds,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime);
}
