package com.example.demo.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Role")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "roleName")
    private String roleName;

}
