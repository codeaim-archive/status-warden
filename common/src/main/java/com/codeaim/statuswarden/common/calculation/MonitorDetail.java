package com.codeaim.statuswarden.common.calculation;

import com.codeaim.statuswarden.common.model.State;
import com.codeaim.statuswarden.common.model.Status;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class MonitorDetail
{
    private final String id;
    private final String userId;
    private final String monitorEventId;
    private final String name;
    private final String url;
    private final State state;
    private final Status status;
    private final String scheduler;
    private final LocalDateTime updated;
    private final LocalDateTime created;
    private final LocalDateTime audit;
    private final LocalDateTime locked;
    private final int interval;
    private final boolean confirming;
    private final int version;
    private final float uptime;
    private final long averageResponseTime;

    public MonitorDetail(
        final String id,
        final String userId,
        final String monitorEventId,
        final String name,
        final String url,
        final State state,
        final Status status,
        final String scheduler,
        final LocalDateTime updated,
        final LocalDateTime created,
        final LocalDateTime audit,
        final LocalDateTime locked,
        final int interval,
        final int version,
        final boolean confirming,
        final float uptime,
        final long averageResponseTime
    )
    {
        this.id = id;
        this.userId = userId;
        this.monitorEventId = monitorEventId;
        this.name = name;
        this.url = url;
        this.state = state;
        this.status = status;
        this.scheduler = scheduler;
        this.updated = updated;
        this.created = created;
        this.audit = audit;
        this.locked = locked;
        this.interval = interval;
        this.version = version;
        this.confirming = confirming;
        this.uptime = uptime;
        this.averageResponseTime = averageResponseTime;
    }

    public String getId()
    {
        return this.id;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public String getMonitorEventId()
    {
        return this.monitorEventId;
    }

    public String getName()
    {
        return this.name;
    }

    public String getUrl()
    {
        return this.url;
    }

    public State getState()
    {
        return this.state;
    }

    public Status getStatus()
    {
        return this.status;
    }

    public String getScheduler()
    {
        return this.scheduler;
    }

    public LocalDateTime getUpdated()
    {
        return this.updated;
    }

    public LocalDateTime getCreated()
    {
        return this.created;
    }

    public LocalDateTime getAudit()
    {
        return this.audit;
    }

    public LocalDateTime getLocked()
    {
        return this.locked;
    }

    public int getInterval()
    {
        return this.interval;
    }

    public int getVersion()
    {
        return this.version;
    }

    public boolean isConfirming()
    {
        return this.confirming;
    }

    public float getUptime()
    {
        return this.uptime;
    }

    public long getAverageResponseTime()
    {
        return this.averageResponseTime;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder
    {
        private String id;
        private String monitorEventId;
        private String userId;
        private String name;
        private String url;
        private State state;
        private Status status;
        private String scheduler;
        private LocalDateTime created;
        private LocalDateTime audit;
        private LocalDateTime locked;
        private int interval;
        private int version;
        private boolean confirming;
        private float uptime;
        private long averageResponseTime;

        public Builder id(final String id)
        {
            this.id = id;
            return this;
        }

        public Builder userId(final String userId)
        {
            this.userId = userId;
            return this;
        }

        public Builder monitorEventId(final String monitorEventId)
        {
            this.monitorEventId = monitorEventId;
            return this;
        }

        public Builder name(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder url(final String url)
        {
            this.url = url;
            return this;
        }

        public Builder state(final State state)
        {
            this.state = state;
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

        public Builder created(final LocalDateTime created)
        {
            this.created = created;
            return this;
        }

        public Builder audit(final LocalDateTime audit)
        {
            this.audit = audit;
            return this;
        }

        public Builder locked(final LocalDateTime locked)
        {
            this.locked = locked;
            return this;
        }

        public Builder interval(final int interval)
        {
            this.interval = interval;
            return this;
        }

        public Builder version(final int version)
        {
            this.version = version;
            return this;
        }

        public Builder confirming(final boolean confirming)
        {
            this.confirming = confirming;
            return this;
        }

        public Builder uptime(final float uptime)
        {
            this.uptime = uptime;
            return this;
        }

        public Builder averageResponseTime(final long averageResponseTime)
        {
            this.averageResponseTime = averageResponseTime;
            return this;
        }

        public MonitorDetail build()
        {
            return new MonitorDetail(
                this.id,
                this.userId,
                this.monitorEventId,
                this.name,
                this.url,
                this.state == null ? State.WAITING : this.state,
                this.status == null ? Status.UNKNOWN : this.status,
                this.scheduler,
                LocalDateTime.now(ZoneOffset.UTC),
                this.created == null ? LocalDateTime.now(ZoneOffset.UTC) : this.created,
                this.audit == null ? LocalDateTime.now(ZoneOffset.UTC) : this.audit,
                this.locked,
                this.interval,
                this.version,
                this.confirming,
                this.uptime,
                this.averageResponseTime
            );
        }
    }
}
