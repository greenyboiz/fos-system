package fpt.edu.capstone.entities;

import fpt.edu.capstone.dto.CurrentUserDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserDetails {
    private CurrentUserDetailsDTO user;
}
