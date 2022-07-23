package fpt.edu.capstone.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="Customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "fullName")
    private String fullName;


//    @Column(name = "contact",unique = true, nullable = false)
    @Column(name = "contact")
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    public Customer(String fullName, String contact, String email, String address) {
        this.fullName = fullName;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }
}
