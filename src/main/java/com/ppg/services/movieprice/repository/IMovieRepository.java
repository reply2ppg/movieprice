package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Movie;

public interface IMovieRepository {
    Movie findById(Integer movieId);
}
