package com.codeaim.statuswarden.common.repository;

import com.codeaim.statuswarden.common.model.Monitor;

import java.time.LocalDateTime;
import java.util.List;

public interface MonitorRepositoryCustom
{
    List<Monitor> findBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
