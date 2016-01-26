package com.codeaim.statuswarden.scheduler.repository;

import org.springframework.data.repository.CrudRepository;

import com.codeaim.statuswarden.scheduler.model.MonitorEvent;

public interface MonitorEventRepository extends CrudRepository<MonitorEvent, String>
{
}

