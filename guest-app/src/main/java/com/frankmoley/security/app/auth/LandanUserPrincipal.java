package com.frankmoley.security.app.auth;

import com.frankmoley.security.app.AuthGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class LandanUserPrincipal implements UserDetails {

    User user;
    List<AuthGroup> authGroupList;

    public LandanUserPrincipal(User user, List<AuthGroup> authGroupList) {
        this.user = user;
        this.authGroupList = authGroupList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null==authGroupList){
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities=new HashSet<>();
        authGroupList.forEach(authGroup -> grantedAuthorities.add(new SimpleGrantedAuthority(authGroup.getAuthGroup())));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
