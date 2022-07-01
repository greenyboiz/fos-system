package com.example.demo.dto;

import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto {

    private Long id;

    private String name;

    private BigDecimal salePrice;

    private BigDecimal costPrice;

}
