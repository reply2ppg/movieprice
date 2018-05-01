package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Movie;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository implements IMovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie findById(Integer movieId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from movie ");
        sb.append("where movieId = ?");
        Movie movie = null;
        try {
            movie = jdbcTemplate.queryForObject(sb.toString(), new Object[]{movieId}, new BeanPropertyRowMapper<>(Movie.class));
        }catch (EmptyResultDataAccessException e){

        }
        return movie;
    }
}
