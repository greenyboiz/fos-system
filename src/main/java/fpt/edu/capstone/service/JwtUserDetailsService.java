package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.CurrentUserDetailsDTO;
import fpt.edu.capstone.entities.CurrentUserDetails;
import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.entities.FOSUserPrincipal;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.repo.FOSUserRepository;
import fpt.edu.capstone.until.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
JWTUserDetailsService implements the Spring Security UserDetailsService interface.
It overrides the loadUserByUsername for fetching user details from the database using the username.
The Spring Security Authentication Manager calls this method for getting the user details from the database
when authenticating the user details provided by the user. Here we are getting the user details from a hardcoded
User List. In the next tutorial we will be adding the DAO implementation for fetching User Details from the Database.
Also the password for a user is stored in encrypted format using BCrypt.
Previously we have seen Spring Boot Security - Password Encoding Using Bcrypt.
Here using the Online Bcrypt Generator you can generate the Bcrypt for a password.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private FOSUserRepository fosUserRepository;

    @Autowired
    private IFOSUserService ifosUserService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FOSUser user = fosUserRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
//        return new User(user.getUserName(),user.getPassword(),authorities);

        FOSUserPrincipal userPrincipal = new FOSUserPrincipal(user);
        return userPrincipal;
    }

    public FOSUser save(FOSUser fosUser) {
//        FOSUser newUser = new FOSUser();
//        newUser.setUserName(fosUser.getUserName());
//        newUser.setPassword(bcryptEncoder.encode(fosUser.getPassword()));
        fosUser.setPassword(bcryptEncoder.encode(fosUser.getPassword()));
        return fosUserRepository.save(fosUser);
    }

    public CurrentUserDetails getCurrentUser(){
        FOSUser fosUser = fosUserRepository.findByUserName(CurrentUser.getCurrentUser().getName());
        if (fosUser == null){
            return null;
        }
        CurrentUserDetailsDTO currentUserDetailsDTO = new CurrentUserDetailsDTO();
        currentUserDetailsDTO.setId(fosUser.getUserId());
        currentUserDetailsDTO.setUsername(fosUser.getUserName());
        currentUserDetailsDTO.setRoleName(fosUser.getRole().getRoleName());

        return new CurrentUserDetails(currentUserDetailsDTO);
    }
}
