package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "QRCode")
@NoArgsConstructor
@AllArgsConstructor
public class QRCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long QRCodeId;

    @Column(name = "QRCodeImage")
    private String QRCodeImage;

    @Column(name = "QRCodeLink")
    private String QRCodeLink;
}
