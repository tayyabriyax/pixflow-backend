package com.practice.pixflow.service;

import com.practice.pixflow.dto.LikeCountDTO;
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

import java.util.List;

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

    public LikeCountDTO likesCount(Integer postId){
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        List<LikeEntity> like = likeRepository.findByPostId(post);
        LikeCountDTO likeCount = new LikeCountDTO();

        if(like != null){
            likeCount.setLikesCount(likeRepository.countByPostId(post));
            likeCount.setLikes(
                    like.stream().map(x -> {
                        LikeDTO likeDTO = new LikeDTO();
                        likeDTO.setId(x.getId());
                        likeDTO.setPost(new PostDTO(
                                x.getPostId().getId(),
                                x.getPostId().getCaption(),
                                x.getPostId().getUrl()
                        ));
                        likeDTO.setUser(new UserDetailsDTO(
                                x.getUserId().getId(),
                                x.getUserId().getUserName(),
                                x.getUserId().getEmail(),
                                x.getUserId().getProfilePic(),
                                x.getUserId().getAbout()
                        ));
                        return likeDTO;
                    }).toList()
            );
        }
        return likeCount;
    }

}
