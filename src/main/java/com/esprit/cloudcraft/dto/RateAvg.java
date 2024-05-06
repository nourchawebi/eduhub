package com.esprit.cloudcraft.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateAvg {
    private Integer rateNumber;
    private Double rateAvg;
}
