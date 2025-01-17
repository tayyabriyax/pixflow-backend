package com.practice.pixflow.service;

import com.practice.pixflow.dto.LikeDTO;
import com.practice.pixflow.dto.PostDTO;
import com.practice.pixflow.dto.UserDetailsDTO;
import com.practice.pixflow.entity.LikeEntity;
import com.practice.pixflow.entity.PostEntity;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.LikeRepository;
import com.practice.pixflow.repository.PostRepository;
import com.practice.pixflow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LikeService {

    @Autowired
    public LikeRepository likeRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    public void likePost(Integer postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userRepository.findUserByUserName(userName);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));;

        if(!likeRepository.existsByUserIdAndPostId(user, post)){
            LikeEntity likes = new LikeEntity();
            likes.setUserId(user);
            likes.setPostId(post);
            likeRepository.save(likes);
        }
    }

    @Transactional
    public void unlikePost(Integer postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userRepository.findUserByUserName(userName);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        likeRepository.deleteByUserIdAndPostId(user, post);
    }

    public LikeDTO likesCount(Integer postId){
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        LikeEntity like = likeRepository.findByPostId(post);
        LikeDTO likeDTO = new LikeDTO();

        likeDTO.setId(like.getId());
        likeDTO.setPost(new PostDTO(
                like.getPostId().getId(),
                like.getPostId().getCaption(),
                like.getPostId().getUrl()));
        likeDTO.setUser(new UserDetailsDTO(
                like.getUserId().getUserName(),
                like.getUserId().getEmail(),
                like.getUserId().getProfilePic(),
                like.getUserId().getAbout()
        ));
        likeDTO.setLikesCount(likeRepository.countByPostId(post));

        return likeDTO;
    }

}
