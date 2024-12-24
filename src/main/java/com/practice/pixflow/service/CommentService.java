package com.practice.pixflow.service;

import com.practice.pixflow.dto.CommentDTO;
import com.practice.pixflow.entity.CommentEntity;
import com.practice.pixflow.entity.PostEntity;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.CommentRepository;
import com.practice.pixflow.repository.PostRepository;
import com.practice.pixflow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CommentService {

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    @Transactional
    public void createComment(CommentDTO comment, Integer postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userRepository.findUserByUserName(userName);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        CommentEntity newComment = new CommentEntity();
        newComment.setContent(comment.getContent());
        newComment.setUserId(user);
        newComment.setPostId(post);

        commentRepository.save(newComment);
    }

}
