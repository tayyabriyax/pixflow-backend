package com.practice.pixflow.service;

import com.practice.pixflow.dto.*;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public void updateUser(UpdateUserDTO user){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            existedUser.setUserName(user.getUserName());
            existedUser.setEmail(user.getEmail());
            existedUser.setAbout(user.getAbout());

            userRepository.save(existedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProfilePic(MultipartFile profilePic) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFileName = profilePic.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + "_" + originalFileName;

            String filePath = uploadDir + uniqueFileName;
            profilePic.transferTo(new File(filePath));

            existedUser.setProfilePic("/upload/" + uniqueFileName);

            userRepository.save(existedUser);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public UserDetailsDTO getUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity existedUser = userRepository.findUserByUserName(userName);
        UserDetailsDTO userDetails = new UserDetailsDTO();
        userDetails.setId(existedUser.getId());
        userDetails.setUserName(existedUser.getUserName());
        userDetails.setEmail(existedUser.getEmail());
        userDetails.setProfilePic(existedUser.getProfilePic());
        userDetails.setAbout(existedUser.getAbout());

        List<PostDTO> posts = existedUser.getPosts().stream().map(x -> {
            PostDTO postsDTO = new PostDTO();
            postsDTO.setId(x.getId());
            postsDTO.setCaption(x.getCaption());
            postsDTO.setUrl(x.getUrl());
            return postsDTO;
        }).toList();
        userDetails.setPosts(posts);

        return userDetails;
    }

    public List<UserDetailsDTO> getSearchedUsers(String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        List<UserEntity> users = userRepository.findAll();

        List<UserDetailsDTO> filteredUsers = users.stream()
                .filter(user -> !Objects.equals(user.getUserName(), userName))
                .filter(user -> user.getUserName().toLowerCase().contains(keyword.toLowerCase()))
                .map(user -> new UserDetailsDTO(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getProfilePic(),
                        user.getAbout()))
                .toList();

        return filteredUsers;
    }
}