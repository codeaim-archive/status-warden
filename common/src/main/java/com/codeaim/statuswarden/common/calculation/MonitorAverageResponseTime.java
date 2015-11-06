package com.codeaim.statuswarden.common.calculation;

public class MonitorAverageResponseTime
{
    private final String monitorId;
    private final long averageResponseTime;

    public MonitorAverageResponseTime(
        final String monitorId,
        final long averageResponseTime
    )
    {
        this.monitorId = monitorId;
        this.averageResponseTime = averageResponseTime;
    }

    public String getMonitorId()
    {
        return this.monitorId;
    }

    public long getAverageResponseTime()
    {
        return this.averageResponseTime;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder
    {
        private String monitorId;
        private long averageResponseTime;

        public Builder monitorId(final String monitorId)
        {
            this.monitorId = monitorId;
            return this;
        }

        public Builder averageResponseTime(final long averageResponseTime)
        {
            this.averageResponseTime = averageResponseTime;
            return this;
        }

        public MonitorAverageResponseTime build()
        {
            return new MonitorAverageResponseTime(
                this.monitorId,
                this.averageResponseTime
            );
        }
    }
}
