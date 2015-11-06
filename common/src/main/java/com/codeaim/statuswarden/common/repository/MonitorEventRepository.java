package com.codeaim.statuswarden.common.repository;

import com.codeaim.statuswarden.common.model.MonitorEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MonitorEventRepository extends MongoRepository<MonitorEvent, String>, MonitorEventRepositoryCustom
{
    @Query("{ monitorId: '?0', created: { $gte: ?1, $lt: ?2 } }")
    List<MonitorEvent> findByMonitorIdBetweenDateTimes(
        String monitorId,
        LocalDateTime startDateTme,
        LocalDateTime endDateTime);
}

