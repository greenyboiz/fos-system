package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Tables")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    @Column(name = "numberOfSeats")
    private Integer numberOfSeats;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "QRCodeID", nullable = true)
    private QRCode qrCode;
}
