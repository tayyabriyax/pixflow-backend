package com.practice.pixflow.service;

import com.practice.pixflow.dto.SignUpDTO;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public void saveUser(SignUpDTO user){
        UserEntity newUser = new UserEntity();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        userRepository.save(newUser);
    }

}
