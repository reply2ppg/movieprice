package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/data.sql")
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:sql/reset.sql")
public class MovieRepositoryTest {
    @Autowired
    IMovieRepository movieRepository;

    @Test
    public void findById_returnsMovie_whenValidIdPassed() throws Exception {
        Movie expectedMovie = Movie.builder()
                .name("Cast Away")
                .rating(4)
                .build();
        Movie actualMovie = movieRepository.findById(6);
        assertThat(actualMovie).isEqualToIgnoringGivenFields(expectedMovie, "movieId");
    }
    @Test
    public void findById_returnsNull_whenInValidIdPassed() throws Exception {
        Movie actualMovie = movieRepository.findById(999);
        assertThat(actualMovie).isNull();
    }

    @Test
    public void findById_returnsNull_whenNullIdPassed() throws Exception {
        assertThat(movieRepository.findById(null)).isNull();
    }
}