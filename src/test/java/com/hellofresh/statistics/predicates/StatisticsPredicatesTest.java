package com.hellofresh.statistics.predicates;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static com.hellofresh.statistics.predicates.StatisticsPredicates.isCSVFormat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

 class StatisticsPredicatesTest {
    @Test
     void testIsCSVFileForValidFile() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getContentType()).thenReturn("text/csv");

        assertTrue(isCSVFormat.test(multipartFile));
    }

    @Test
     void testIsCSVFileForInValidFile() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getContentType()).thenReturn("pdf");

        assertFalse(isCSVFormat.test(multipartFile));
    }
}
