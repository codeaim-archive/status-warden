package com.codeaim.statuswarden.common.repository;

import com.codeaim.statuswarden.common.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;

public class MonitorRepositoryImpl implements MonitorRepositoryCustom
{
    @Autowired
    MonitorRepository monitorRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Monitor> findBetween(LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        return monitorRepository.findAll();
    }
}
