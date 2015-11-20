package com.codeaim.statuswarden.common.repository;

import com.codeaim.statuswarden.common.model.MonitorEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MonitorEventRepository extends MongoRepository<MonitorEvent, String>, MonitorEventRepositoryCustom
{
    Optional<MonitorEvent> findById(String id);

    @Query("{ monitorId: '?0', created: { $gte: ?1, $lt: ?2 } }")
    List<MonitorEvent> findByMonitorIdBetweenDateTimes(
        String monitorId,
        LocalDateTime startDateTme,
        LocalDateTime endDateTime,
        Sort sort);

    @Query("{ monitorId: { $in: ?0 }, changed: true, confirmation: true, created: { $gte: ?1, $lt: ?2 } }")
    List<MonitorEvent> findByMonitorIdsChangedBetweenDateTimes(
        List<String> monitorIds,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Sort sort);

    @Query("{ monitorId: '?0', changed: true, confirmation: true, created: { $gte: ?1, $lt: ?2 } }")
    List<MonitorEvent> findByMonitorIdChangedBetweenDateTimes(
        String monitorId,
        LocalDateTime startDateTme,
        LocalDateTime endDateTime,
        Sort sort);

    @Query("{ monitorId: '?0', created: { $lt: ?1 } }")
    Page<MonitorEvent> findByMonitorIdBeforeDateTime(
        String monitorId,
        LocalDateTime dateTime,
        Pageable pageable);

    @Query("{ monitorId: { $in: ?0 }, created: { $gte: ?1 } }")
    Page<MonitorEvent> findByMonitorIdsAfterDateTime(
        List<String> monitorIds,
        LocalDateTime dateTime,
        Pageable pageable);
}

