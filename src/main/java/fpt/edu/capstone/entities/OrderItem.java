package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "OrderItem")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = true)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "dishesId", nullable = true)
    private Dishes dishes;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;
//
//    @Column(name = "orderItemStatus")
//    private Integer orderItemStatus;

    @Column(name = "createDate")
    private Date createDate = new Date(System.currentTimeMillis());

    @Column(name = "lastUpdateDate")
    private Date lastUpdateDate;

//    @Transient
//    public BigDecimal getSubTotal(){
//        return dishes.getSalePrice()* BigDecimal.va;
//    }
}
