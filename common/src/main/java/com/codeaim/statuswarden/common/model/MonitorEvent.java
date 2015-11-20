package com.codeaim.statuswarden.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class MonitorEvent
{
    @Id
    private final String id;
    @Indexed
    private final String monitorId;
    @Indexed
    private final String previous;
    private final Status status;
    private final String scheduler;
    private final int statusCode;
    private final long responseTime;
    private final boolean changed;
    private final boolean confirmation;
    private final LocalDateTime created;

    public MonitorEvent(
        final String id,
        final String monitorId,
        final String previous,
        final Status status,
        final String scheduler,
        final int statusCode,
        final long responseTime,
        final boolean changed,
        final boolean confirmation,
        final LocalDateTime created
    )
    {
        this.id = id;
        this.monitorId = monitorId;
        this.previous = previous;
        this.status = status;
        this.scheduler = scheduler;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.changed = changed;
        this.confirmation = confirmation;
        this.created = created;
    }

    public String getId()
    {
        return this.id;
    }

    public String getMonitorId()
    {
        return this.monitorId;
    }

    public String getPrevious()
    {
        return this.previous;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public String getScheduler()
    {
        return this.scheduler;
    }

    public int getStatusCode()
    {
        return this.statusCode;
    }

    public long getResponseTime()
    {
        return this.responseTime;
    }

    public boolean isChanged()
    {
        return this.changed;
    }

    public boolean isConfirmation()
    {
        return this.confirmation;
    }

    public LocalDateTime getCreated()
    {
        return this.created;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder
    {
        private String id;
        private String monitorId;
        private String previous;
        private Status status;
        private String scheduler;
        private int statusCode;
        private long responseTime;
        private boolean changed;
        private boolean confirmation;

        public Builder monitorId(final String monitorId)
        {
            this.monitorId = monitorId;
            return this;
        }

        public Builder previous(final String previous)
        {
            this.previous = previous;
            return this;
        }

        public Builder status(final Status status)
        {
            this.status = status;
            return this;
        }

        public Builder scheduler(final String scheduler)
        {
            this.scheduler = scheduler;
            return this;
        }

        public Builder statusCode(final int statusCode)
        {
            this.statusCode = statusCode;
            return this;
        }

        public Builder responseTime(final long responseTime)
        {
            this.responseTime = responseTime;
            return this;
        }

        public Builder changed(final boolean changed)
        {
            this.changed = changed;
            return this;
        }

        public Builder confirmation(final boolean confirmation)
        {
            this.confirmation = confirmation;
            return this;
        }

        public MonitorEvent build()
        {
            return new MonitorEvent(
                this.id,
                this.monitorId,
                this.previous,
                this.status,
                this.scheduler,
                this.statusCode,
                this.responseTime,
                this.changed,
                this.confirmation,
                LocalDateTime.now(ZoneOffset.UTC)
            );
        }
    }
}
