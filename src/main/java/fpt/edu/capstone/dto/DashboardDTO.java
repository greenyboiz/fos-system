package fpt.edu.capstone.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class DashboardDTO {
    private Integer month; // order
    private Integer year;
    private BigDecimal revenue; // sum(sale_price của tổng các món ăn trong list order)
    private BigDecimal cost;
    private BigDecimal profit;
}
