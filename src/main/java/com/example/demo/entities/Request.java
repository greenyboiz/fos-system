package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Request")
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @Column(name = "requestType")
    private String requestType;

    @Column(name = "requestDetail")
    private String requestDetail;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;
}
