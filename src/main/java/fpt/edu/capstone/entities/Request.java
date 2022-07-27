package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Request")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "requestTypeId", nullable = true)
    private RequestType requestType;

    @Column(name = "requestDetail")
    private String requestDetail;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = true)
    private Customer customer;
}
