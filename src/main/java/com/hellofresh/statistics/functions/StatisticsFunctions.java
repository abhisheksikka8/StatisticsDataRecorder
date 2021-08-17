package com.hellofresh.statistics.functions;

import com.hellofresh.statistics.constants.StatisticsEventProcessingStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongFunction;

import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_CORRECT;
import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_MALFORMED;
import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.PARTIAL_RECORDS_MALFORMED;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsFunctions {
    public static final Function<String, Double> toDoubleValue = Double::valueOf;
    public static final Function<String, Long> toLongValue = Long::valueOf;

    public static final LongFunction<LocalDateTime> toLocalDateTime = timestamp ->
            LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                    TimeZone.getDefault().toZoneId());

    public static final BiFunction<List<CSVRecord>, Integer, StatisticsEventProcessingStatus> validateProcessingResults =
            (malFormedRecords, totalRecords) -> {
                if (!malFormedRecords.isEmpty()) {
                    if (malFormedRecords.size() == totalRecords) {
                        log.error("All Entries are malformed. Records: {}", malFormedRecords);
                        return ALL_RECORDS_MALFORMED;
                    }

                    log.error("Malformed Entries Found: {}", malFormedRecords);
                    return PARTIAL_RECORDS_MALFORMED;
                }

                return ALL_RECORDS_CORRECT;
            };
}
