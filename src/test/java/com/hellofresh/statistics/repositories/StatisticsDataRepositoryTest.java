package com.hellofresh.statistics.repositories;

import com.hellofresh.statistics.entity.StatisticData;
import com.hellofresh.statistics.repository.StatisticsDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class StatisticsDataRepositoryTest {
    @Autowired
    private StatisticsDataRepository statisticsDataRepository;

    @Test
     void testSaveAll() {
        CopyOnWriteArrayList<StatisticData> statisticDataList = new CopyOnWriteArrayList<>();
        statisticDataList.add(StatisticData.builder().xValue(2.0)
                .yValue(2L).occurrenceTimestamp(LocalDateTime.now()).build());
        statisticsDataRepository.saveAll(statisticDataList);

        //when(statisticsDataRepository.getStatisticDataList()).thenReturn(statisticDataList);
        assertEquals(3, statisticsDataRepository.getStatisticDataList().size());
    }

    @Test
     void testGetStatisticsDataForLastMinute() {
        List<StatisticData> statisticDataList = new ArrayList<>();
        statisticDataList.add(StatisticData.builder().xValue(21.0)
                .yValue(22L).occurrenceTimestamp(LocalDateTime.now()).build());

        statisticDataList.add(StatisticData.builder().xValue(2.0)
                .yValue(2L).occurrenceTimestamp(LocalDateTime.now().plusMinutes(1)).build());
       statisticsDataRepository.saveAll(statisticDataList);
        //when(statisticsDataRepository.getStatisticsDataForLastMinute()).thenReturn(statisticDataList);
        assertEquals(2, statisticsDataRepository.getStatisticsDataForLastMinute().size());
    }
}
