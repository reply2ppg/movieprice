package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Movie;
import com.ppg.services.movieprice.entity.Theater;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TheaterRepository implements ITheaterRepository {
    private final JdbcTemplate jdbcTemplate;

    public TheaterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Theater findById(Integer theaterId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from theater ");
        sb.append("where theaterId = ?");
        Theater theater = null;
        try {
            theater = jdbcTemplate.queryForObject(sb.toString(), new Object[]{theaterId}, new BeanPropertyRowMapper<>(Theater.class));
        }catch (EmptyResultDataAccessException e){
        }
        return theater;
    }
}

