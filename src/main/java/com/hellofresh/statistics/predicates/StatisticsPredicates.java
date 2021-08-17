package com.hellofresh.statistics.predicates;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsPredicates {
    private static final String TYPE = "text/csv";
    public static final Predicate<MultipartFile> isCSVFormat = file ->
            TYPE.equals(file.getContentType());
}
