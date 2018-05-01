package com.ppg.services.movieprice.controller;

import com.ppg.services.movieprice.entity.Price;
import com.ppg.services.movieprice.repository.IPriceRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
public class MoviePriceController {

    private final IPriceRepository priceRepository;

    Pattern CHECK_SPECIAL_CHARS = Pattern.compile("[A-Za-z0-9-\\s]*");

    public MoviePriceController(IPriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    @GetMapping("/movieprices")
    @ApiOperation(value = "Gets movie price for given movie, theater and city", response = Price.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad or malformed request"),
            @ApiResponse(code = 406, message = "Not Acceptable"),
            @ApiResponse(code = 500, message = "Failure")})

    public ResponseEntity<List<Price>> getMoviePrices(
            @RequestParam(value = "movie", required = false) String movie,
            @RequestParam(value = "theater", required = false) String theater,
            @RequestParam(value = "city", required = false) String city
            ){
        List<Price> prices = Collections.EMPTY_LIST;
        Triple<String, String, String> criteria;
        if(StringUtils.isAllBlank(movie, theater, city)) {
            criteria = Triple.of(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        }else if((StringUtils.isNotBlank(movie) && !CHECK_SPECIAL_CHARS.matcher(movie).matches()) ||
                (StringUtils.isNotBlank(theater) && !CHECK_SPECIAL_CHARS.matcher(theater).matches()) ||
                (StringUtils.isNotBlank(city) && !CHECK_SPECIAL_CHARS.matcher(city).matches()))
            {
                return new ResponseEntity<>(prices, HttpStatus.BAD_REQUEST);
            } else {
            criteria = Triple.of(movie, theater, city);
        }
    try{
            prices = priceRepository.findMoviePrices(criteria);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }
}
