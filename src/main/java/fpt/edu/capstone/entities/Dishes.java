package fpt.edu.capstone.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@Table(name = "Dishes")
@NoArgsConstructor
@AllArgsConstructor
public class Dishes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishesId;

    @Column(name = "dishesName")
    private String dishesName;

    @Column(name = "description")
    private String description;

    @Column(name = "dishImage")
    private String dishImage;

    @Column(name = "salePrice")
    private BigDecimal salePrice;

    @Column(name = "costPrice")
    private BigDecimal costPrice;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = true)
    private Category category;

    public Dishes(String dishesName, String description, String dishImage, BigDecimal salePrice, BigDecimal costPrice, Long discount, boolean status, Category category) {
        this.dishesName = dishesName;
        this.description = description;
        this.dishImage = dishImage;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.discount = discount;
        this.status = status;
        this.category = category;
    }
}
