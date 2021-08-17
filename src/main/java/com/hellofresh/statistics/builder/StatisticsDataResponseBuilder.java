package com.hellofresh.statistics.builder;

import com.hellofresh.statistics.response.StatisticsDataResponse;

import java.math.BigDecimal;

public class StatisticsDataResponseBuilder {
    private Integer totalRecords;
    private BigDecimal sumOfX;
    private BigDecimal avgOfX;
    private Long sumOfY;
    private BigDecimal avgOfY;

    public StatisticsDataResponseBuilder totalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
        return this;
    }

    public StatisticsDataResponseBuilder sumOfX(Double valueOfX) {
        this.sumOfX = BigDecimal.valueOf(valueOfX).setScale(10, BigDecimal.ROUND_CEILING);
        return this;
    }

    public StatisticsDataResponseBuilder sumOfY(Long valueOfY) {
        this.sumOfY = valueOfY;
        return this;
    }

    public StatisticsDataResponseBuilder avgOfX(Double valueOfX, int noOfElements) {
        this.avgOfX = BigDecimal.valueOf(valueOfX / noOfElements).setScale(10, BigDecimal.ROUND_CEILING);
        return this;
    }

    public StatisticsDataResponseBuilder avgOfY(Long valueOfY, int noOfElements) {
        this.avgOfY= BigDecimal.valueOf(valueOfY / noOfElements);
        return this;
    }

    public StatisticsDataResponse build() {
        return new StatisticsDataResponse(totalRecords, sumOfX, avgOfX, sumOfY, avgOfY);
    }
}
