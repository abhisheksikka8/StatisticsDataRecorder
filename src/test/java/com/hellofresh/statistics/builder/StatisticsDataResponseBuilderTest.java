package com.hellofresh.statistics.builder;

import com.hellofresh.statistics.response.StatisticsDataResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class StatisticsDataResponseBuilderTest {
    private StatisticsDataResponseBuilder statisticsDataResponseBuilder = new StatisticsDataResponseBuilder();

    @Test
     void testTotalRecords() {
        Integer totalRec = 2;
        StatisticsDataResponse statisticsDataResponse =
                statisticsDataResponseBuilder.totalRecords(2).build();
        assertEquals(totalRec, statisticsDataResponse.getTotalRecords());
    }

    @Test
     void testAverageOfX() {
        BigDecimal avgX = BigDecimal.valueOf(1.0).setScale(10);
        StatisticsDataResponse statisticsDataResponse =
                statisticsDataResponseBuilder.avgOfX(2.0, 2).build();
        assertEquals(avgX, statisticsDataResponse.getAvgOfX());
    }

    @Test
     void testAverageOfY() {
        BigDecimal avgY = BigDecimal.valueOf(1L);
        StatisticsDataResponse statisticsDataResponse =
                statisticsDataResponseBuilder.avgOfY(2L, 2).build();
        assertEquals(avgY, statisticsDataResponse.getAvgOfY());
    }

    @Test
     void testSumOfX() {
        BigDecimal sumOfX = BigDecimal.valueOf(2.0).setScale(10);
        StatisticsDataResponse statisticsDataResponse =
                statisticsDataResponseBuilder.sumOfX(2.0).build();
        assertEquals(sumOfX, statisticsDataResponse.getSumOfX());
    }

    @Test
     void testSumOfY() {
        Long sumOfY = 2L;
        StatisticsDataResponse statisticsDataResponse =
                statisticsDataResponseBuilder.sumOfY(2L).build();
        assertEquals(sumOfY, statisticsDataResponse.getSumOfY());
    }
}
