package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Price;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public interface IPriceRepository {
    List<Price> findMoviePrices(Triple<String, String, String> searchCriteria);

    Integer insertPrice(Integer movieId, Integer theateId, Double price);
}
