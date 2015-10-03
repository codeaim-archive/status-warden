package com.codeaim.statuswarden.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @ToString
public class Monitor
{
    @Id
    private String id;
    private String name;
    private String url;
    @Setter
    private State state = State.WAITING;
    @Setter
    private Status status =  Status.UNKNOWN;
    @Setter
    private String scheduler;
    @Setter
    private Date updated = new Date();
    private Date created = new Date();
    @Setter
    private Date audit = new Date();
    @Setter
    private Date locked;
    @Setter
    private int interval;
    @Setter
    private boolean confirming;
    @Setter @Version
    private int version;
    @Getter @Setter
    private List<MonitorEvent> events = new ArrayList<>();

    private Monitor() {}

    @Builder
    private Monitor(
        String name,
        String url,
        int interval
    )
    {
        this.name = name;
        this.url = url;
        this.interval = interval;
    }
}
