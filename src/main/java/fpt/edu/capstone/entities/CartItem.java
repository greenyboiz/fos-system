package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "CartItem")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "dishesId", nullable = true)
    private Dishes dishes;

    @ManyToOne
    @JoinColumn(name = "QRCodeId", nullable = true)
    private QRCode qrCode;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;

    @Column(name = "quantity")
    private Integer quantity;

    @Transient
    public BigDecimal getSubTotal(){
        return BigDecimal.valueOf(dishes.getSalePrice() * quantity);
    }
}
