package com.practice.pixflow.controller;

import com.practice.pixflow.service.LikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@Tag(name = "Like")
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

    @GetMapping("/like-count/{post_id}")
    public ResponseEntity<?> likesCount(@PathVariable(name = "post_id") Integer postId){
        try{
            return new ResponseEntity<>("Post Likes : " + likeService.likesCount(postId),
                    HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
