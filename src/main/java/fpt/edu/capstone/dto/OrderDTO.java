package fpt.edu.capstone.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderDTO {
    private Long orderId;
    private String fullName;
    private String contact;
    private Date submitDate;
    private String employee;
    private String paymentTypeName;
    private BigDecimal totalMoneyOfOrder;
}
