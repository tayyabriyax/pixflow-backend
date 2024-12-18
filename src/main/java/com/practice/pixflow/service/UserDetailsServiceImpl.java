package com.practice.pixflow.service;

import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity existedUser = userRepository.findUserByUserName(username);
        if(existedUser != null){
            return User.builder()
                    .username(existedUser.getUserName())
                    .password(existedUser.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("User not found!");
    }

}