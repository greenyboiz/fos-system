package fpt.edu.capstone.dto;

import fpt.edu.capstone.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserDetailsDTO {
    private Long id;
    private String username;
//    private Collection<Role> roles;
    private String roleName;
}
