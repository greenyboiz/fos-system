package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "Payment")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = true)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private FOSUser fosUser;

    @ManyToOne
    @JoinColumn(name = "paymentTypeId", nullable = true)
    private PaymentType paymentType;

    @Column(name = "totalMoney")
    private BigDecimal totalMoney;

}
