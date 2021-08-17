package com.hellofresh.statistics.service;

import com.hellofresh.statistics.constants.StatisticsEventProcessingStatus;
import com.hellofresh.statistics.response.StatisticsDataResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StatisticsDataService {
    StatisticsEventProcessingStatus saveStatistics(MultipartFile statisticsDataFile);

    Optional<StatisticsDataResponse> getStatistics();
}
