package com.codeaim.statuswarden.api.controller;

import com.codeaim.statuswarden.common.model.Monitor;
import com.codeaim.statuswarden.common.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/monitor")
public class MonitorController
{
    @Autowired
    private MonitorRepository monitorRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Monitor> findAll()
    {
        return monitorRepository.findAll();
    }

    @RequestMapping("/{monitorId}")
    public Monitor findById(@PathVariable(value = "monitorId") String monitorId)
    {
        return monitorRepository.findOne(monitorId);
    }
}
