package fpt.edu.capstone.dto;

import lombok.Data;

@Data
public class FOSUserDTO {
    private Long userId;
    private String fullName;
    private String userName;
    private String password;
    private Boolean gender;
    private String contact;
    private String email;
    private Boolean status;
    private String profileImage;

    private String oldPassword;

    private String newPassword;

    private String confirmNewPassword;

}
