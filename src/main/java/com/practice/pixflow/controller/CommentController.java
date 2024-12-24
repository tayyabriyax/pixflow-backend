package com.practice.pixflow.controller;

import com.practice.pixflow.dto.CommentDTO;
import com.practice.pixflow.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    public CommentService commentService;

    @PostMapping("/create-comment/{post_id}")
    public ResponseEntity<?> createEntity(@RequestBody CommentDTO comment,
                                          @PathVariable(name = "post_id") Integer postId) {
        try {
            commentService.createComment(comment, postId);
            return new ResponseEntity<>("Comment created Successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}