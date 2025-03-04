package com.practice.pixflow.controller;

import com.practice.pixflow.service.FollowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@Tag(name = "Follow")
public class FollowController {

    @Autowired
    public FollowService followService;

    @PostMapping("/follow/{following_id}")
    public ResponseEntity<?> followUser(@PathVariable(name = "following_id") Integer followingId ){
        try{
            followService.followUser(followingId);
            return new ResponseEntity<>("User Followed !", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/unfollow/{following_id}")
    public ResponseEntity<?> unFollowUser(@PathVariable(name = "following_id") Integer followingId ){
        try{
            followService.unFollowUser(followingId);
            return new ResponseEntity<>("User Unfollowed !", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers(){
        try{
            return new ResponseEntity<>(followService.getFollowers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/following")
    public ResponseEntity<?> getFollowing(){
        try{
            return new ResponseEntity<>(followService.getFollowing(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}