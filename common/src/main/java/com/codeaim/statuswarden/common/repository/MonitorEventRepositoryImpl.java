package com.codeaim.statuswarden.common.repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import com.codeaim.statuswarden.common.calculation.MonitorAverageResponseTime;
import com.codeaim.statuswarden.common.model.MonitorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDateTime;
import java.util.List;

public class MonitorEventRepositoryImpl implements MonitorEventRepositoryCustom
{
    @Autowired
    MonitorEventRepository monitorEventRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<MonitorAverageResponseTime> findByMonitorIdsCalculateAverageResponseTimeBetweenDateTimes(
        List<String> monitorIds,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime)
    {
        TypedAggregation<MonitorEvent> aggregation = newAggregation(MonitorEvent.class,
            match(Criteria
                .where("monitorId")
                .in(monitorIds)
                .and("created")
                .gte(startDateTime)
                .lt(endDateTime)),
            group("monitorId")
                .avg("responseTime")
                .as("averageResponseTime"),
            project("averageResponseTime")
                .and("_id")
                .as("monitorId"));

        return mongoTemplate
            .aggregate(aggregation, "monitorEvent", MonitorAverageResponseTime.class)
            .getMappedResults();
    }
}
