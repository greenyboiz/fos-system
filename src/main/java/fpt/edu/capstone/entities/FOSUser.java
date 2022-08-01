package fpt.edu.capstone.entities;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Data
@Entity
@Table(name="FOSUser")
@NoArgsConstructor
@AllArgsConstructor
public class FOSUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "contact")
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "profileImage")
    private String profileImage;

    @ManyToOne
    @JoinColumn(name = "roleId", nullable = true)
    private Role role;

    public FOSUser(String fullName, String userName, String password, Boolean gender, String contact, String email, Boolean status, String profileImage, Role role) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.status = status;
        this.profileImage = profileImage;
        this.role = role;
    }

//    public boolean isActive(){
//        if(this.status == true){
//            return true;
//        }
//        return false;
//    }
}