package com.practice.pixflow.controller;

import com.practice.pixflow.service.BookmarkService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmark")
@Tag(name = "Bookmark")
public class BookmarkController {

    @Autowired
    public BookmarkService bookmarkService;

    @PostMapping("/add-bookmark/{post_id}")
    public ResponseEntity<?> addBookmark(@PathVariable(name = "post_id") Integer postId) {
        try {
            bookmarkService.addBookmark(postId);
            return new ResponseEntity<>("Bookmark is added Successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/remove-bookmark/{post_id}")
    public ResponseEntity<?> removeBookmark(@PathVariable(name = "post_id") Integer postId) {
        try {
            bookmarkService.removeBookmark(postId);
            return new ResponseEntity<>("Bookmark is removed Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/get-bookmark")
    public ResponseEntity<?> getBookmark() {
        try {
            return new ResponseEntity<>(bookmarkService.getBookmark(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
