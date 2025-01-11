package com.practice.pixflow.controller;

import com.practice.pixflow.dto.UpdateUserDTO;
import com.practice.pixflow.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
public class UserController {

    @Autowired
    public UserService userService;

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestParam("userName") String userName,
                                        @RequestParam("email") String email,
                                        @RequestParam("password") String password,
                                        @RequestParam("about") String about,
                                        @RequestParam("profilePic") MultipartFile profilePic){
        try{
            UpdateUserDTO newUser = new UpdateUserDTO();
            newUser.setUserName(userName);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setAbout(about);

            userService.updateUser(newUser, profilePic);
            return new ResponseEntity<>("User is Successfully Updated!", HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/user-details")
    public ResponseEntity<?> getUserDetails(){
        try{
            return new ResponseEntity<>(userService.getUserDetails(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}