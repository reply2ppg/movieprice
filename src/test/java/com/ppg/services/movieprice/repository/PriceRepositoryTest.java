package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Movie;
import com.ppg.services.movieprice.entity.Price;
import com.ppg.services.movieprice.entity.Theater;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/data.sql")
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/reset.sql")

public class PriceRepositoryTest {

    @Autowired
    IPriceRepository priceRepository;

    @Autowired
    IMovieRepository movieRepository;

    @Autowired
    ITheaterRepository theaterRepository;

    Movie.MovieBuilder movieBuilder;
    Theater.TheaterBuilder theaterBuilder;

    @Before
    public void setUp(){
    movieBuilder = Movie.builder();
    theaterBuilder = Theater.builder();
    }



    @Test
    public void findMoviePrices_returnsAllPrices_whenNoCriteriaPassed() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(new Integer(1), new Integer(2), new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(new Integer(2), new Integer(3), new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(new Integer(3), new Integer(4), new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY));
        assertThat(priceList.size()).isEqualTo(3);

        Price price1 = priceList.get(0);
        Movie movie1 = movieRepository.findById(1);
        Theater theater1 = theaterRepository.findById(2);

        assertThat(price1.getPriceId()).isEqualTo(priceId1);
        assertThat(price1.getMovieName()).isEqualTo(movie1.getName());
        assertThat(price1.getCityName()).isEqualTo(theater1.getCity());
        assertThat(price1.getTheaterName()).isEqualTo(theater1.getName());
        assertThat(price1.getPrice()).isEqualTo(new Double("200"));

        Price price2 = priceList.get(1);
        Movie movie2 = movieRepository.findById(2);
        Theater theater2 = theaterRepository.findById(3);

        assertThat(price2.getPriceId()).isEqualTo(priceId2);
        assertThat(price2.getMovieName()).isEqualTo(movie2.getName());
        assertThat(price2.getCityName()).isEqualTo(theater2.getCity());
        assertThat(price2.getTheaterName()).isEqualTo(theater2.getName());
        assertThat(price2.getPrice()).isEqualTo(new Double("300"));

        Price price3 = priceList.get(2);
        Movie movie3 = movieRepository.findById(3);
        Theater theater3 = theaterRepository.findById(4);

        assertThat(price3.getPriceId()).isEqualTo(priceId3);
        assertThat(price3.getMovieName()).isEqualTo(movie3.getName());
        assertThat(price3.getCityName()).isEqualTo(theater3.getCity());
        assertThat(price3.getTheaterName()).isEqualTo(theater3.getName());
        assertThat(price3.getPrice()).isEqualTo(new Double("400"));
}

    @Test
    public void findMoviePrices_returnsPrices_whenValidMovieIsGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 3, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(1, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of("Sholay", null, null));
        assertThat(priceList.size()).isEqualTo(2);
        assertThat(priceList.get(0).getPriceId()).isEqualTo(priceId1);
        assertThat(priceList.get(1).getPriceId()).isEqualTo(priceId3);

        priceList = priceRepository.findMoviePrices(Triple.of("Roja", null, null));
        assertThat(priceList.size()).isEqualTo(1);
        assertThat(priceList.get(0).getPriceId()).isEqualTo(priceId2);

        priceList = priceRepository.findMoviePrices(Triple.of("None", null, null));
        assertThat(priceList.size()).isEqualTo(0);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidMovieWithDifferentCaseGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 3, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(1, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of("SHOLAY", null, null));
        assertThat(priceList.size()).isEqualTo(2);

        priceList = priceRepository.findMoviePrices(Triple.of("roja", null, null));
        assertThat(priceList.size()).isEqualTo(1);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidMovieWithPartialNameGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 3, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(1, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of("sho", null, null));
        assertThat(priceList.size()).isEqualTo(2);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidTheaterIsGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 4, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(3, 4, new Double("400"));
        Integer priceId4 = priceRepository.insertPrice(4, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(null, "Royal", null));
        assertThat(priceList.size()).isEqualTo(3);
       assertThat(priceList.get(0).getPriceId()).isEqualTo(priceId2);
        assertThat(priceList.get(1).getPriceId()).isEqualTo(priceId3);
        assertThat(priceList.get(2).getPriceId()).isEqualTo(priceId4);

        priceList = priceRepository.findMoviePrices(Triple.of(null, "AMC", null));
        assertThat(priceList.size()).isEqualTo(1);
        assertThat(priceList.get(0).getPriceId()).isEqualTo(priceId1);

        priceList = priceRepository.findMoviePrices(Triple.of(null, "none", null));
        assertThat(priceList.size()).isEqualTo(0);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidThaterWithDifferentCaseGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 4, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(3, 4, new Double("400"));
        Integer priceId4 = priceRepository.insertPrice(4, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(null, "ROYAL", null));
        assertThat(priceList.size()).isEqualTo(3);

        priceList = priceRepository.findMoviePrices(Triple.of(null, "amc", null));
        assertThat(priceList.size()).isEqualTo(1);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidTheaterWithPartialNameGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 4, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(3, 4, new Double("400"));
        Integer priceId4 = priceRepository.insertPrice(4, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(null, "roy", null));
        assertThat(priceList.size()).isEqualTo(3);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidCityIsGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 4, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(3, 4, new Double("400"));
        Integer priceId4 = priceRepository.insertPrice(4, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(null, null, "Columbus"));
        assertThat(priceList.size()).isEqualTo(3);
        assertThat(priceList.get(0).getPriceId()).isEqualTo(priceId2);
        assertThat(priceList.get(1).getPriceId()).isEqualTo(priceId3);
        assertThat(priceList.get(2).getPriceId()).isEqualTo(priceId4);

        priceList = priceRepository.findMoviePrices(Triple.of(null, null, "Dayton"));
        assertThat(priceList.size()).isEqualTo(1);
        assertThat(priceList.get(0).getPriceId()).isEqualTo(priceId1);

        priceList = priceRepository.findMoviePrices(Triple.of(null, null, "None"));
        assertThat(priceList.size()).isEqualTo(0);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidCityWithDifferentCaseGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 4, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(3, 4, new Double("400"));
        Integer priceId4 = priceRepository.insertPrice(4, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(null, null, "COLUMBUS"));
        assertThat(priceList.size()).isEqualTo(3);

        priceList = priceRepository.findMoviePrices(Triple.of(null, null, "dayton"));
        assertThat(priceList.size()).isEqualTo(1);
    }

    @Test
    public void findMoviePrices_returnsPrices_whenValidCityWithPartialNameGiven() throws Exception {
        Integer priceId1 = priceRepository.insertPrice(1, 2, new Double("200"));
        Integer priceId2 = priceRepository.insertPrice(2, 4, new Double("300"));
        Integer priceId3 = priceRepository.insertPrice(3, 4, new Double("400"));
        Integer priceId4 = priceRepository.insertPrice(4, 4, new Double("400"));

        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(null, null, "Col"));
        assertThat(priceList.size()).isEqualTo(3);
    }




    @Test
    public void findMoviePrices_returnsEmptyList_whenNoDataAvailableAndNoCriteriaPassed() throws Exception {
        List<Price> priceList = priceRepository.findMoviePrices(Triple.of(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY));
        assertThat(priceList.size()).isEqualTo(0);
    }


    @Test
    public void insert_returnInsertedRecordId_whenMovieIdTheaterIdAndPricePassed() throws Exception {
        Integer insertedPriceId1 = priceRepository.insertPrice(new Integer(1), new Integer(2), new Double("200"));
        assertThat(insertedPriceId1).isNotNull();
        Integer insertedPriceId2 = priceRepository.insertPrice(new Integer(2), new Integer(3), new Double("300"));
        assertThat(insertedPriceId1).isNotEqualTo(insertedPriceId2);
    }

    @Test
    public void insert_returnNull_whenMovieIdIsNull() throws Exception {
        Integer insertedPriceId1 = priceRepository.insertPrice(null, new Integer(2), new Double("200"));
        assertThat(insertedPriceId1).isNull();
    }

    @Test
    public void insert_returnNull_whenTheaterIdIsNull() throws Exception {
        Integer insertedPriceId1 = priceRepository.insertPrice(new Integer(1), null, new Double("200"));
        assertThat(insertedPriceId1).isNull();
    }

    @Test
    public void insert_returnInsertedRecordId_whenNullPricePassed() throws Exception {
        Integer insertedPriceId1 = priceRepository.insertPrice(new Integer(1), new Integer(1), null);
        assertThat(insertedPriceId1).isNotNull();
    }
}