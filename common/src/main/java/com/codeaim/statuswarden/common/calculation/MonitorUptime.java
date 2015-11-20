package com.codeaim.statuswarden.common.calculation;

public class MonitorUptime
{
    private final String monitorId;
    private final float uptime;

    public MonitorUptime(
        final String monitorId,
        final float uptime
    )
    {
        this.monitorId = monitorId;
        this.uptime = uptime;
    }

    public String getMonitorId()
    {
        return this.monitorId;
    }

    public float getUptime()
    {
        return this.uptime;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder
    {
        private String monitorId;
        private float uptime;

        public Builder monitorId(final String monitorId)
        {
            this.monitorId = monitorId;
            return this;
        }

        public Builder uptime(final float uptime)
        {
            this.uptime = uptime;
            return this;
        }

        public MonitorUptime build()
        {
            return new MonitorUptime(
                this.monitorId,
                this.uptime
            );
        }
    }
}
