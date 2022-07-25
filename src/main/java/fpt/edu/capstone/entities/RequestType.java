package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(name = "RequestType")
@NoArgsConstructor
@AllArgsConstructor
public class RequestType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestTypeId;

    @Column(name = "requestTypeName")
    private String requestTypeName;
}
