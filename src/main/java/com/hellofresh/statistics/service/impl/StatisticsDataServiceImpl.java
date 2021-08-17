package com.hellofresh.statistics.service.impl;

import com.hellofresh.statistics.constants.StatisticsEventProcessingStatus;
import com.hellofresh.statistics.entity.StatisticData;
import com.hellofresh.statistics.exception.StatisticEventException;
import com.hellofresh.statistics.provider.StatisticsDataProvider;
import com.hellofresh.statistics.repository.StatisticsDataRepository;
import com.hellofresh.statistics.response.StatisticsDataResponse;
import com.hellofresh.statistics.service.StatisticsDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_CORRECT;
import static com.hellofresh.statistics.functions.StatisticsFunctions.validateProcessingResults;

@Slf4j
@Service
public class StatisticsDataServiceImpl implements StatisticsDataService {
    @Autowired
    private StatisticsDataProvider statisticsDataProvider;

    @Autowired
    private StatisticsDataRepository statisticsDataRepository;

    @Override
    public StatisticsEventProcessingStatus saveStatistics(MultipartFile statisticsDataFile) {
        log.info("Saving Statistics Data");

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                statisticsDataFile.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);) {
            List<CSVRecord> statistics = csvParser.getRecords();
            List<StatisticData> validRecords = new ArrayList<>();
            List<CSVRecord> malFormedRecords = new ArrayList<>();

            this.processRecords(statistics, validRecords, malFormedRecords);

            StatisticsEventProcessingStatus eventProcessingStatus =
                    validateProcessingResults.apply(malFormedRecords, statistics.size());

            if(ALL_RECORDS_CORRECT.equals(eventProcessingStatus)) {
                 statisticsDataRepository.saveAll(validRecords);
                log.info("Data Saved successfully");
            }

            return eventProcessingStatus;
        } catch (IOException ioEx) {
            log.error("IO Exception occurred");
            throw new StatisticEventException("IO Exception occurred");
        }
    }

    @Override
    public Optional<StatisticsDataResponse> getStatistics() {
        List<StatisticData> matchingStatisticsList = this.statisticsDataRepository
                .getStatisticsDataForLastMinute();

        if(matchingStatisticsList.isEmpty()) {
            return Optional.empty();
        }
        Double x = 0.0;
        Long y = 0L;
        int matchingRecSize = matchingStatisticsList.size();

        for (StatisticData statisticsData : matchingStatisticsList) {
            x += statisticsData.getXValue();
            y += statisticsData.getYValue();
        }

        return Optional.of(StatisticsDataResponse.builder().sumOfX(x).sumOfY(y)
                .avgOfX(x, matchingRecSize).avgOfY(y, matchingRecSize)
                .totalRecords(matchingRecSize).build());
    }

    private void processRecords(List<CSVRecord> statistics,
                                List<StatisticData> validRecords,
                                List<CSVRecord> malformedRecords) {
        for(CSVRecord statistic : statistics) {
            try {
                validRecords.add(statisticsDataProvider.getStatisticsData(statistic));
            } catch (StatisticEventException | DateTimeException | NumberFormatException statisticEventEx) {
                log.error("Invalid Statistics Event Found: {}", statistic);
                malformedRecords.add(statistic);
            }
        }
    }
}
