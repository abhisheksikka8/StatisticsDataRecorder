package com.hellofresh.statistics.provider;

import com.hellofresh.statistics.entity.StatisticData;
import com.hellofresh.statistics.exception.StatisticEventException;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.hellofresh.statistics.functions.StatisticsFunctions.toDoubleValue;
import static com.hellofresh.statistics.functions.StatisticsFunctions.toLocalDateTime;
import static com.hellofresh.statistics.functions.StatisticsFunctions.toLongValue;

@Component
public class StatisticsDataProvider {
    @Value("${statistics.data.parameters.length}")
    private Integer paraLength;

    public StatisticData getStatisticsData (CSVRecord statisticData) {

        if(statisticData.size() < paraLength) {
            throw new StatisticEventException("Invalid Data Received");
        }

        return StatisticData.builder().occurrenceTimestamp(toLocalDateTime
                .apply(Long.parseLong(statisticData.get(0))))
                .xValue(toDoubleValue.apply(statisticData.get(1)))
                .yValue(toLongValue.apply(statisticData.get(2))).build();
    }
}
