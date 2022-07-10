package fpt.edu.capstone.dto;

import fpt.edu.capstone.entities.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class ListDto {
    private List<OrderItem> list;
}
