package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Theater;

public interface ITheaterRepository {
        Theater findById(Integer theaterId);
}
