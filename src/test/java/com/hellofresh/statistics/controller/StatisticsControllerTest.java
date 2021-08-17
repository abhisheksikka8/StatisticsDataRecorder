
package com.hellofresh.statistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellofresh.statistics.response.StatisticsDataResponse;
import com.hellofresh.statistics.service.StatisticsDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.InputStream;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StatisticsDataService statisticsDataService;


    @Test
    void testSaveStatisticsData() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("test.csv");

        MockMultipartFile mockMultipartFile = new MockMultipartFile("statisticsEvents", "test.csv", "text/csv" , is);

        mockMvc.perform(multipart("/event").file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(202))
                .andDo(print());;
       ;
    }

   @Test
   void testGetStatisticsDataForValidData() throws Exception {
      Optional<StatisticsDataResponse> statisticsDataResponse = Optional.of(StatisticsDataResponse.builder()
              .avgOfX(2.0, 1).avgOfY(2L,1).totalRecords(1)
      .sumOfX(2.0).sumOfY(2L).build());

      when(statisticsDataService.getStatistics()).thenReturn(statisticsDataResponse);
      mockMvc.perform(get("/stats")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().is(200))
              .andDo(print());
   }

   @Test
   void testGetStatisticsDataFor() throws Exception {
      Optional<StatisticsDataResponse> statisticsDataResponse = Optional.of(StatisticsDataResponse.builder()
              .avgOfX(2.0, 1).avgOfY(2L,1).totalRecords(1)
              .sumOfX(2.0).sumOfY(2L).build());

      when(statisticsDataService.getStatistics()).thenReturn(statisticsDataResponse);
      mockMvc.perform(get("/stats")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().is(200))
              .andDo(print());
   }
}

