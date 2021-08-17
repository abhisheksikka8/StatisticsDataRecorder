package com.hellofresh.statistics.repository;

import com.hellofresh.statistics.entity.StatisticData;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
@Getter
public class StatisticsDataRepository {
    @Value("${statistics.fetch.time.seconds}")
    private Integer recordFetchTime;
    private CopyOnWriteArrayList<StatisticData> statisticDataList;

    public StatisticsDataRepository () {
        statisticDataList = new CopyOnWriteArrayList<>();
    }

    public void saveAll(List<StatisticData> statsDataList) {
        statisticDataList.addAll(statsDataList);
    }

    public List<StatisticData> getStatisticsDataForLastMinute() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        return statisticDataList.stream()
                .filter(statisticData -> ChronoUnit.SECONDS.between(now,
                        statisticData.getOccurrenceTimestamp()) <= recordFetchTime).collect(Collectors.toList());
    }
}
