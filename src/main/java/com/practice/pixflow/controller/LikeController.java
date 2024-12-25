package com.practice.pixflow.controller;

import com.practice.pixflow.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/unlike-post/{post_id}")
    public ResponseEntity<?> unlikePost(@PathVariable(name = "post_id") Integer postId){
        try{
            likeService.unlikePost(postId);
            return new ResponseEntity<>("Post Unliked !", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
