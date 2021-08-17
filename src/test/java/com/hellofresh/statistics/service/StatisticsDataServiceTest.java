package com.hellofresh.statistics.service;

import com.hellofresh.statistics.entity.StatisticData;
import com.hellofresh.statistics.exception.StatisticEventException;
import com.hellofresh.statistics.provider.StatisticsDataProvider;
import com.hellofresh.statistics.repository.StatisticsDataRepository;
import com.hellofresh.statistics.service.impl.StatisticsDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_CORRECT;
import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_MALFORMED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class StatisticsDataServiceTest {
    @Mock
    private StatisticsDataProvider statisticsDataProvider;

    @Mock
    private StatisticsDataRepository statisticsDataRepository;

    @InjectMocks
    private StatisticsDataServiceImpl statisticsDataService;

    @Test
     void testSaveStatistics() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("test.csv");
        when(multipartFile.getInputStream()).thenReturn(is);

        assertEquals(ALL_RECORDS_CORRECT, statisticsDataService.saveStatistics(multipartFile));
    }

    @Test
     void testSaveStatisticsForMalformed() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        InputStream is = getClass().getClassLoader().getResourceAsStream("test_malformed.csv");
        when(multipartFile.getInputStream()).thenReturn(is);
        when(statisticsDataProvider.getStatisticsData(any()))
                .thenThrow(new StatisticEventException("Invalid Data Received"));

        assertEquals(ALL_RECORDS_MALFORMED, statisticsDataService.saveStatistics(multipartFile));
    }

    @Test
     void testGetStatistics() {
        List<StatisticData> statisticDataList = new ArrayList<>();
        statisticDataList.add(StatisticData.builder().xValue(21.0)
                .yValue(22L).occurrenceTimestamp(LocalDateTime.now()).build());
        statisticDataList.add(StatisticData.builder().xValue(2.0)
                .yValue(2L).occurrenceTimestamp(LocalDateTime.now().plusMinutes(1)).build());

        when(statisticsDataRepository.getStatisticsDataForLastMinute()).thenReturn(statisticDataList);

        System.out.println(statisticsDataService.getStatistics());
        assertTrue(statisticsDataService.getStatistics().isPresent());
        Integer totRec = 2;
        assertEquals(totRec, statisticsDataService.getStatistics().get().getTotalRecords());
    }
}
