package com.hellofresh.statistics.functions;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_CORRECT;
import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.ALL_RECORDS_MALFORMED;
import static com.hellofresh.statistics.constants.StatisticsEventProcessingStatus.PARTIAL_RECORDS_MALFORMED;
import static com.hellofresh.statistics.functions.StatisticsFunctions.toDoubleValue;
import static com.hellofresh.statistics.functions.StatisticsFunctions.toLongValue;
import static com.hellofresh.statistics.functions.StatisticsFunctions.validateProcessingResults;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class StatisticFunctionsTest {
    @Test
      void testToDoubleValue() {
        Double expectedVal = 2.0;
        assertEquals(expectedVal, toDoubleValue.apply("2.0"));
    }

    @Test
     void testToLongValue() {
        Long expectedVal = 2L;
        assertEquals(expectedVal, toLongValue.apply("2"));
    }

    @Test
     void testToLocalDateTime() {
        LocalDateTime time = StatisticsFunctions.toLocalDateTime.apply(1628856310374L);
        assertEquals(LocalDate.of(2021, 8, 13), time.toLocalDate());
        assertEquals("17:35:10.374", time.toLocalTime().toString());
    }

    @Test
     void testValidateProcessingResultsForAllMalformedRecords() throws IOException {
        CSVParser csvParser = this.getCSVRecords("test.csv");
        assertEquals(ALL_RECORDS_MALFORMED, validateProcessingResults.apply(csvParser.getRecords(), 50));
    }

    @Test
     void testValidateProcessingResultsForPartialMalformedRecords() throws IOException {
         CSVParser csvParser = this.getCSVRecords("test.csv");
         assertEquals(PARTIAL_RECORDS_MALFORMED, validateProcessingResults.apply(csvParser.getRecords(), 100));
     }

     @Test
      void testValidateProcessingResultsForAllCorrectRecords() throws IOException {
        assertEquals(ALL_RECORDS_CORRECT, validateProcessingResults.apply(Collections.emptyList(), 50));
     }

    private CSVParser getCSVRecords(String testFileName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(testFileName);
        return new CSVParser(new BufferedReader(new
                InputStreamReader(is, StandardCharsets.UTF_8)), CSVFormat.DEFAULT);
    }
}
