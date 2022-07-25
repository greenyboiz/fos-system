package fpt.edu.capstone.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsePayment {
    private String status;
    private String message;
    private boolean success;
    private BigDecimal payment;
    private Long tableId;
}
