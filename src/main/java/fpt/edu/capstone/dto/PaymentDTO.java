package fpt.edu.capstone.dto;

import fpt.edu.capstone.entities.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PaymentDTO {
    private List<OrderItem> orderItemList;
    private BigDecimal payment;
    private Long tableId;

}
