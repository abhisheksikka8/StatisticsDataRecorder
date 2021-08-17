package com.hellofresh.statistics.controller;

import com.hellofresh.statistics.constants.StatisticsEventProcessingStatus;
import com.hellofresh.statistics.response.StatisticsDataResponse;
import com.hellofresh.statistics.service.StatisticsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_MALFORMED;
import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.INVALID_FILE_FORMAT;
import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.PARTIAL_RECORDS_MALFORMED;
import static com.hellofresh.statistics.predicates.StatisticsPredicates.isCSVFormat;

@RestController
public class StatisticsController {
    @Autowired
    private StatisticsDataService statisticsDataService;

    @PostMapping("/event")
    public ResponseEntity<StatisticsEventProcessingStatus> saveStatisticsData(@RequestParam("statisticsEvents") MultipartFile statisticsEventsData) {
        if(!isCSVFormat.test(statisticsEventsData)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_FILE_FORMAT);
        }

        StatisticsEventProcessingStatus statisticsEventProcessingStatus = statisticsDataService.saveStatistics(statisticsEventsData);

        if(ALL_RECORDS_MALFORMED.equals(statisticsEventProcessingStatus)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(statisticsEventProcessingStatus);
        }

        if(PARTIAL_RECORDS_MALFORMED.equals(statisticsEventProcessingStatus)) {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(statisticsEventProcessingStatus);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(statisticsEventProcessingStatus);
    }

    @GetMapping("/stats")
    public ResponseEntity<String> retrieveStatisticsData() {
        Optional<StatisticsDataResponse> statisticsDataResponse = statisticsDataService.getStatistics();

        if(!statisticsDataResponse.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(statisticsDataResponse.get().toString());
    }
}
