package fpt.edu.capstone.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class FOSUserPrincipal implements UserDetails {
    private FOSUser fosUser;

    public FOSUserPrincipal(FOSUser fosUser) {
        this.fosUser = fosUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add((GrantedAuthority)new SimpleGrantedAuthority(this.fosUser.getRole().getRoleName()));
        return authorities;
    }


    @Override
    public String getPassword() {
        return this.fosUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.fosUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
