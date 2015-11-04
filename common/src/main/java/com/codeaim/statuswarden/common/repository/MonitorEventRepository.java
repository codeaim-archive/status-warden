package com.codeaim.statuswarden.common.repository;

import com.codeaim.statuswarden.common.model.MonitorEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MonitorEventRepository extends MongoRepository<MonitorEvent, String> {}

