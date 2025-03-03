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
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO newUser){
        try{
            userService.updateUser(newUser);
            return new ResponseEntity<>("User is Successfully Updated!", HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/update-profile-pic")
    public ResponseEntity<?> updateProfilePic(@RequestParam("profilePic") MultipartFile profilePic){
        try{
            userService.updateProfilePic(profilePic);
            return new ResponseEntity<>("Profile Pic is Successfully Updated!", HttpStatus.OK);
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

    @GetMapping("search-user/{keyword}")
    public ResponseEntity<?> getSearchedUsers(@PathVariable String keyword){
        try{
            return new ResponseEntity<>(userService.getSearchedUsers(keyword), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}