package fpt.edu.capstone.entities;

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

    @ManyToOne
    @JoinColumn(name = "requestTypeId", nullable = true)
    private Request request;

    @Column(name = "requestDetail")
    private String requestDetail;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;
}
