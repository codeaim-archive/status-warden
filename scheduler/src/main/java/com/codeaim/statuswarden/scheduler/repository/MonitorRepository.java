package com.codeaim.statuswarden.scheduler.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.codeaim.statuswarden.scheduler.model.Monitor;

public interface MonitorRepository extends CrudRepository<Monitor, String>
{
    Page<Monitor> findAll(Pageable pageRequest);

//    @Query("{ $or: [ { state: 'WAITING', audit: { $lte: ?0 } }, { state: 'ELECTED', locked: { $lte: ?0 } } ] }")
//    Page<Monitor> findElectable(LocalDateTime currentDate, Pageable pageable);
//
//    @Query("{ $and: [ { $or: [ { state: 'WAITING', audit: { $lte: ?0 } }, { state: 'ELECTED', locked: { $lte: ?0 } } ] }, { $or: [ { confirming: false }, { scheduler: { $ne: ?1 } } ] } ] }")
//    Page<Monitor> findElectableClustered(LocalDateTime currentDate, String scheduler, Pageable pageable);
}
