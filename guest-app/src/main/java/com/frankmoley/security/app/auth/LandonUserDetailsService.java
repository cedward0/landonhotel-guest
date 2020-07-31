package com.frankmoley.security.app.auth;

import com.frankmoley.security.app.AuthGroupRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LandonUserDetailsService implements UserDetailsService {
    
    UserRepository userRepository;
    AuthGroupRepository authGroupRepository;

    public LandonUserDetailsService(UserRepository userRepository, AuthGroupRepository authGroupRepository) {
        this.userRepository = userRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new LandanUserPrincipal(Optional.of(this.userRepository.findByUsername(username))
                .orElseThrow(()->new UsernameNotFoundException("cannot find user")),
                authGroupRepository.findByUsername(username));
    }
}
