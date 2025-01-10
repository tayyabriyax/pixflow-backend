package com.practice.pixflow.controller;

import com.practice.pixflow.dto.CreatePostDTO;
import com.practice.pixflow.dto.EditPostDTO;
import com.practice.pixflow.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@Tag(name = "Post")
public class PostController {

    @Autowired
    public PostService postService;

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestParam("caption") String caption,
                                        @RequestParam("post") MultipartFile postFile) {
        try {
            CreatePostDTO newPost = new CreatePostDTO();
            newPost.setCaption(caption);
            postService.createPost(newPost, postFile);
            return new ResponseEntity<>("Post is successfully Created!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/edit-post/{post_id}")
    public ResponseEntity<?> editPost(@PathVariable("post_id") Integer id,
                                      @RequestParam("caption") String caption,
                                      @RequestParam("post") MultipartFile postFile) {
        try {
            EditPostDTO newPost = new EditPostDTO();
            newPost.setId(id);
            newPost.setCaption(caption);
            postService.updatePost(newPost, postFile);
            return new ResponseEntity<>("Post is successfully Updated!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/delete-post/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable("post_id") Integer id) {
        try {
            postService.removePost(id);
            return new ResponseEntity<>("Post is successfully Deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/get-post")
    public ResponseEntity<?> getPosts() {
        try {
            return new ResponseEntity<>(postService.getPosts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
