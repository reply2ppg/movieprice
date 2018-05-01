package com.ppg.services.movieprice.entity;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {
    private Integer movieId;
    private String name;
    private Integer rating;
}
