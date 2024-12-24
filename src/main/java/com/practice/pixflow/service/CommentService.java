package com.practice.pixflow.service;

import com.practice.pixflow.dto.*;
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

import java.util.List;
import java.util.Objects;

@Component
public class CommentService {

    @Autowired
    public CommentRepository commentRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    @Transactional
    public void createComment(CreateCommentDTO comment, Integer postId) {
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

    @Transactional
    public void updateComment(CreateCommentDTO commentDTO, Integer commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer userId = userRepository.findUserByUserName(userName).getId();

        CommentEntity existedComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (Objects.equals(existedComment.getUserId().getId(), userId)) {
            existedComment.setContent(commentDTO.getContent());
        }

        commentRepository.save(existedComment);
    }

    @Transactional
    public void deleteComment(Integer commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer userId = userRepository.findUserByUserName(userName).getId();

        CommentEntity existedComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (Objects.equals(existedComment.getUserId().getId(), userId)) {
            commentRepository.deleteById(commentId);
        }
    }

    public List<GetCommentDTO> getComments(Integer postId) {
        List<CommentEntity> comments = commentRepository.findByPostId(new PostEntity(postId));

        return comments.stream()
                .map(comment -> {
                    UserEntity user = comment.getUserId();

                    List<PostDTO> posts = user.getPosts()
                            .stream()
                            .map(post -> new PostDTO(post.getId(), post.getCaption(), post.getUrl()))
                            .toList();

                    UserDetailsDTO userDetailsDTO = new UserDetailsDTO(
                            user.getUserName(),
                            user.getEmail(),
                            user.getProfilePic(),
                            user.getAbout(),
                            posts
                    );

                    return new GetCommentDTO(comment.getContent(), userDetailsDTO);
                }).toList();

    }

}
