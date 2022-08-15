package fpt.edu.capstone.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BestSelerDishesDTO {
    private String dishesName;
    private Integer quantity;
}
