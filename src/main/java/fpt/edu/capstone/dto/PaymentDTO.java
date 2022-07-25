package fpt.edu.capstone.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDTO {
    private BigDecimal payment;
    private Long tableId;
}
