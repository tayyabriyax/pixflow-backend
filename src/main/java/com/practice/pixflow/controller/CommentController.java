package com.practice.pixflow.controller;

import com.practice.pixflow.dto.CreateCommentDTO;
import com.practice.pixflow.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment")
public class CommentController {

    @Autowired
    public CommentService commentService;

    @PostMapping("/create-comment/{post_id}")
    public ResponseEntity<?> createEntity(@RequestBody CreateCommentDTO comment,
                                          @PathVariable(name = "post_id") Integer postId) {
        try {
            commentService.createComment(comment, postId);
            return new ResponseEntity<>("Comment created Successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/edit-comment/{comment_id}")
    public ResponseEntity<?> editComment(@RequestBody CreateCommentDTO comment,
                                          @PathVariable(name = "comment_id") Integer commentId) {
        try {
            commentService.updateComment(comment, commentId);
            return new ResponseEntity<>("Comment updated Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/delete-comment/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "comment_id") Integer commentId) {
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>("Comment deleted Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/get-comment/{post_id}")
    public ResponseEntity<?> getComment(@PathVariable(name = "post_id") Integer postId) {
        try {
            return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}