package com.ppg.services.movieprice.entity;

import lombok.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Theater {
    private Integer theaterId;
    private String name;
    private String city;
}
