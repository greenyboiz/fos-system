package fpt.edu.capstone.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BestSellerDishesDTO {
    private String dishesName;
    private Integer quantity;
}
