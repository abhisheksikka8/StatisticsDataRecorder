package com.hellofresh.statistics.response;

import com.hellofresh.statistics.builder.StatisticsDataResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class StatisticsDataResponse {
    private Integer totalRecords;
    private BigDecimal sumOfX;
    private BigDecimal avgOfX;
    private Long sumOfY;
    private BigDecimal avgOfY;

    public static StatisticsDataResponseBuilder builder() {
        return new StatisticsDataResponseBuilder();
    }

    @Override
    public String toString() {
        return totalRecords + "," + sumOfX + "," + avgOfX + "," + sumOfY + "," + avgOfY;
    }
}
