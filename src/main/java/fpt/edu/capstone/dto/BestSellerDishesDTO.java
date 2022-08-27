package fpt.edu.capstone.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BestSellerDishesDTO {
    private String dishesName;
    private String dishesImage;
    private Integer quantity;
    private BigDecimal salePrice;

}
