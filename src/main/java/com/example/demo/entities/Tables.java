package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Tables")
@NoArgsConstructor
@AllArgsConstructor
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    @Column(name = "numberOfSeats")
    private String numberOfSeats;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "QRCodeID", nullable = true)
    private QRCode qrCode;
}
