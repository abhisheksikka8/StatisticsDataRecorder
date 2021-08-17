package com.hellofresh.statistics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StatisticsDataRecorderApplication {
    public static void main(String [] args) {
        log.info("Starting StatisticsDataRecorderApplication ...");
        SpringApplication.run(StatisticsDataRecorderApplication.class);
    }
}
