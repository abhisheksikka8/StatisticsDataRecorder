package com.hellofresh.statistics.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class StatisticData {
    private LocalDateTime occurrenceTimestamp;
    private Double xValue;
    private Long yValue;
}
