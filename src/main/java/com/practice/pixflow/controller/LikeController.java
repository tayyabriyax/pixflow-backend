package com.practice.pixflow.controller;

import com.practice.pixflow.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    public LikeService likeService;

    @PostMapping("/like-post/{post_id}")
    public ResponseEntity<?> likePost(@PathVariable(name = "post_id") Integer postId){
        try{
            likeService.likePost(postId);
            return new ResponseEntity<>("Post Liked !", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
