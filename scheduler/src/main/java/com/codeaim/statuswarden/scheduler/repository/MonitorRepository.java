package com.codeaim.statuswarden.scheduler.repository;

import com.codeaim.statuswarden.scheduler.model.Monitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MonitorRepository extends CrudRepository<Monitor, String>
{
    @Query(" SELECT m " +
            "FROM Monitor m " +
            "WHERE ((m.state = com.codeaim.statuswarden.scheduler.model.State.WAITING " +
            "           AND m.audit <= :currentDate) " +
            "       OR (m.state = com.codeaim.statuswarden.scheduler.model.State.ELECTED " +
            "           AND m.locked <= :currentDate)) " +
            "   AND ((:isClustered = false) " +
            "       OR (m.confirming = false)" +
            "       OR  (:isClustered = true " +
            "           AND m.scheduler <> :scheduler ))"
    )
    Page<Monitor> findElectable(
            @Param("scheduler") String scheduler,
            @Param("isClustered") boolean isClustered,
            @Param("currentDate") LocalDateTime currentDate,
            Pageable pageRequest
    );
}
