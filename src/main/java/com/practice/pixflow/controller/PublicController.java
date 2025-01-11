package com.practice.pixflow.controller;

import com.practice.pixflow.dto.SignInDTO;
import com.practice.pixflow.dto.SignUpDTO;
import com.practice.pixflow.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Public")
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO user){
        try{
            userService.saveUser(user);
            return new ResponseEntity<>("User is Signed Up Successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIp(@RequestBody SignInDTO user){
        try{
            return new ResponseEntity<>(userService.login(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}