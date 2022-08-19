package fpt.edu.capstone.dto;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.entities.QRCode;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderDTO {
    private Long orderId;
    private String fullName;
//    private QRCode qrCode;
    private String contact;
    private Date submitTime;
    private Date submitDate;
    private BigDecimal totalMoneyOfOrder;
}
