package com.javagrind.oauth2practice.security.services;

import com.javagrind.oauth2practice.entity.UserEntity;
import com.javagrind.oauth2practice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByEmail(email);
        if(user==null)
            throw new UsernameNotFoundException("User Not Found with email: " + email);

        return UserDetailsImpl.build(user);
    }

}
