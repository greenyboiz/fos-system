package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "QRCodeId", nullable = true)
    private QRCode qrCode;

    @Column(name = "submitTime")
    private Date submitTime = new Date(System.currentTimeMillis());

    @Column(name = "closeTime")
    private Date closeTime;

    @Column(name = "otherFee")
    private BigDecimal otherFee;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "description")
    private String description;

    @Column(name = "orderDiscount")
    private Integer orderDiscount;
}
