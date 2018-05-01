package com.ppg.services.movieprice.controller;

import com.google.gson.Gson;
import com.ppg.services.movieprice.MoviepriceApplication;
import com.ppg.services.movieprice.entity.Price;
import com.ppg.services.movieprice.repository.IPriceRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MoviepriceApplication.class)
@AutoConfigureMockMvc

public class MoviePriceControllerTest {
    private MockMvc mockMvc;
    Gson gson = new Gson();

    @Mock
    private IPriceRepository priceRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MoviePriceController(priceRepository))
                .build();
        }

    @Test
    public void getMoviePrices_returnsAllPrices_whenNoFilterCriteria() throws Exception {
        List<Price> expectedPrices = asList(
                Price.builder()
                .priceId(1)
                .cityName("Cincinnati")
                .theaterName("AMC")
                .movieName("Sholay")
                .price(new Double("100.00"))
                .build() ,
                Price.builder()
                .priceId(2)
                .cityName("Dayton")
                .theaterName("AMC")
                .movieName("Roja")
                .price(new Double("200.00"))
                .build());
        when(priceRepository.findMoviePrices(Triple.of(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY))).thenReturn(expectedPrices);
        MvcResult mvcResult = mockMvc.perform(get("/movieprices"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponseString = gson.toJson(expectedPrices);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase(expectedResponseString);
    }

    @Test
    public void getMoviePrices_returnsEmptyList_whenNoPricesAvailable() throws Exception {
        List<Price> expectedPrices = Collections.EMPTY_LIST;
        when(priceRepository.findMoviePrices(Triple.of(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY))).thenReturn(expectedPrices);
        MvcResult mvcResult = mockMvc.perform(get("/movieprices"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedResponseString = gson.toJson(expectedPrices);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(expectedResponseString);
    }

    @Test
    public void getMoviePrices_returnsPrices_whenMovieIsPassed() throws Exception {
        List<Price> expectedPrices = asList(
                Price.builder()
                        .priceId(1)
                        .cityName("Cincinnati")
                        .theaterName("AMC")
                        .movieName("Sholay")
                        .price(new Double("100.00"))
                        .build() ,
                Price.builder()
                        .priceId(2)
                        .cityName("Dayton")
                        .theaterName("AMC")
                        .movieName("Sholay")
                        .price(new Double("200.00"))
                        .build());
        when(priceRepository.findMoviePrices(Triple.of("Sholay", null, null))).thenReturn(expectedPrices);
        MvcResult mvcResult = mockMvc.perform(get("/movieprices")
                .param("movie", "Sholay"))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponseString = gson.toJson(expectedPrices);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase(expectedResponseString);
    }

    @Test
    public void getMoviePrices_returnsPrices_whenTheaterAndCityPassed() throws Exception {
        List<Price> expectedPrices = asList(
                Price.builder()
                        .priceId(1)
                        .cityName("Cincinnati")
                        .theaterName("AMC")
                        .movieName("Sholay")
                        .price(new Double("100.00"))
                        .build() ,
                Price.builder()
                        .priceId(2)
                        .cityName("Cincinnati")
                        .theaterName("AMC")
                        .movieName("IT")
                        .price(new Double("200.00"))
                        .build());
        when(priceRepository.findMoviePrices(Triple.of(null, "AMC", "Cincinnati"))).thenReturn(expectedPrices);
        MultiValueMap<String, String> theaterAndCity = new LinkedMultiValueMap();
        theaterAndCity.add("theater", "AMC");
        theaterAndCity.add("city", "Cincinnati");
        MvcResult mvcResult = mockMvc.perform(get("/movieprices")
                .params(theaterAndCity))
                .andExpect(status().isOk())
                .andReturn();

        String expectedResponseString = gson.toJson(expectedPrices);
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringCase(expectedResponseString);
    }


    @Test
    public void getMoviePrices_returns500Error_whenDatabaseExceptionOccurs() throws Exception {
        when(priceRepository.findMoviePrices(Triple.of(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY))).thenThrow(new RuntimeException());
        MvcResult mvcResult = mockMvc.perform(get("/movieprices"))
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(500);
    }

    @Test
    public void getMoviePrices_returnsError_whenSpecialCharaterIsGiven() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movieprices")
                .param("movie","ABC**" ))
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
        verify(priceRepository, never()).findMoviePrices(any(Triple.class));

        mvcResult = mockMvc.perform(get("/movieprices")
                .param("theater","ABC**" ))
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
        verify(priceRepository, never()).findMoviePrices(any(Triple.class));

        mvcResult = mockMvc.perform(get("/movieprices")
                .param("city","ABC**" ))
                .andReturn();
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
        verify(priceRepository, never()).findMoviePrices(any(Triple.class));
    }
}