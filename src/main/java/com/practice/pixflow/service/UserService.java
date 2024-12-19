package com.practice.pixflow.service;

import com.practice.pixflow.dto.SignInDTO;
import com.practice.pixflow.dto.SignUpDTO;
import com.practice.pixflow.dto.UpdateUserDTO;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.UserRepository;
import com.practice.pixflow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtUtil jwtUtil;

    final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(SignUpDTO user){
        UserEntity newUser = new UserEntity();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
    }

    public String login(SignInDTO user) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        UserDetails userDetails =  userDetailsService.loadUserByUsername(user.getUserName());
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    public void updateUser(UpdateUserDTO user, MultipartFile profilePic){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            String  fileName = profilePic.getOriginalFilename();
            String absolutePath = new File("src/main/resources/static/upload/").getAbsolutePath();
            String filePath = absolutePath + "/" + fileName;
            profilePic.transferTo(new File(filePath));

            existedUser.setUserName(user.getUserName());
            existedUser.setEmail(user.getEmail());
            existedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existedUser.setProfilePic(filePath);
            existedUser.setAbout(user.getAbout());

            userRepository.save(existedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
