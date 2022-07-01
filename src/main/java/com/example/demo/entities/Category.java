package com.example.demo.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "categoryName")
    private String categoryName;

    @Column(name = "categoryImage")
    private String categoryImage;
}
