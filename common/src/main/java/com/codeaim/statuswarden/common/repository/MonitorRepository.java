package com.codeaim.statuswarden.common.repository;

import com.codeaim.statuswarden.common.model.Monitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;

public interface MonitorRepository extends MongoRepository<Monitor, String>, MonitorRepositoryCustom
{
    @Query("{ $or: [ { state: 'WAITING', audit: { $lte: ?0 } }, { state: 'ELECTED', locked: { $lte: ?0 } } ] }")
    Page<Monitor> findElectable(LocalDateTime currentDate, Pageable pageable);

    @Query("{ $and: [ { $or: [ { state: 'WAITING', audit: { $lte: ?0 } }, { state: 'ELECTED', locked: { $lte: ?0 } } ] }, { $or: [ { confirming: false }, { scheduler: { $ne: ?1 } } ] } ] }")
    Page<Monitor> findElectableClustered(LocalDateTime currentDate, String scheduler, Pageable pageable);
}
