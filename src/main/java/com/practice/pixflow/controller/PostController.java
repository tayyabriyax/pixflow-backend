package com.practice.pixflow.controller;

import com.practice.pixflow.dto.CreatePostDTO;
import com.practice.pixflow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    public PostService postService;

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestParam("caption") String caption,
                                        @RequestParam("post")MultipartFile postFile){
       try{
           CreatePostDTO newPost = new CreatePostDTO();
           newPost.setCaption(caption);
           postService.createPost(newPost, postFile);
           return new ResponseEntity<>("Post is successfully Created!", HttpStatus.CREATED);
       } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
       }
    }

}
