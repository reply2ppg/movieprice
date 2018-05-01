package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Theater;
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
public class TheaterRepositoryTest {
    @Autowired
    ITheaterRepository theaterRepository;

    @Test
    public void findById_returnsTheater_whenValidIdPassed() throws Exception {
        Theater expectedTheater = Theater.builder()
                .name("AMC")
                .city("Cincinnati")
                .build();

        Theater actualTheater = theaterRepository.findById(1);
        assertThat(actualTheater).isNotNull();
        assertThat(actualTheater).isNotEqualTo(theaterRepository.findById(2));
        assertThat(actualTheater).isEqualToIgnoringGivenFields(expectedTheater, "theaterId");
    }

    @Test
    public void findById_returnsNull_whenInValidIdPassed() throws Exception {
        Theater actualTheater = theaterRepository.findById(999);
        assertThat(actualTheater).isNull();
    }

    @Test
    public void findById_returnsNull_whenNullIdPassed() throws Exception {
        assertThat(theaterRepository.findById(null)).isNull();
    }


}