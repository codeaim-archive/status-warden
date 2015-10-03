package com.codeaim.statuswarden.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter @ToString
public class MonitorEvent
{
    private Status status;
    private String scheduler;
    private int statusCode;
    private long responseTime;
    private boolean changed;
    private boolean confirmation;
    private Date created;

    private MonitorEvent() {}

    @Builder
    public MonitorEvent(
        Status status,
        String scheduler,
        int statusCode,
        long responseTime,
        boolean changed,
        boolean confirmation,
        Date created)
    {
        this.status = status;
        this.scheduler = scheduler;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.created = created != null ? created : new Date();
        this.changed = changed;
        this.confirmation = confirmation;
    }
}
