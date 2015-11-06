package com.codeaim.statuswarden.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class Monitor
{
    @Id
    private final String id;
    @Indexed
    private final String userId;
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
    @Version
    private final int version;

    public Monitor(
        final String id,
        final String userId,
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
        final boolean confirming
    )
    {
        this.id = id;
        this.userId = userId;
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
    }

    public String getId()
    {
        return this.id;
    }

    public String getUserId()
    {
        return this.userId;
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

    public static Builder builder() { return new Builder(); }

    public static Builder buildFrom(Monitor monitor)
    {
        return builder()
            .id(monitor.getId())
            .userId(monitor.getUserId())
            .name(monitor.getName())
            .url(monitor.getUrl())
            .state(monitor.getState())
            .status(monitor.getStatus())
            .scheduler(monitor.getScheduler())
            .created(monitor.getCreated())
            .audit(monitor.getAudit())
            .locked(monitor.getLocked())
            .interval(monitor.getInterval())
            .version(monitor.getVersion())
            .confirming(monitor.isConfirming());
    }

    public static class Builder
    {
        private String id;
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

        private Builder id(final String id)
        {
            this.id = id;
            return this;
        }

        public Builder userId(final String userId)
        {
            this.userId = userId;
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

        private Builder created(final LocalDateTime created)
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

        private Builder version(final int version)
        {
            this.version = version;
            return this;
        }

        public Builder confirming(final boolean confirming)
        {
            this.confirming = confirming;
            return this;
        }

        public Monitor build()
        {
            return new Monitor(
                this.id,
                this.userId,
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
                this.confirming
            );
        }
    }
}
