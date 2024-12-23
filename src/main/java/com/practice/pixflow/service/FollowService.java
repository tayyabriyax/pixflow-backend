package com.practice.pixflow.service;

import com.practice.pixflow.dto.FollowerDTO;
import com.practice.pixflow.dto.FollowingDTO;
import com.practice.pixflow.dto.PostDTO;
import com.practice.pixflow.dto.UserDetailsDTO;
import com.practice.pixflow.entity.FollowEntity;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.FollowRepository;
import com.practice.pixflow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FollowService {

    @Autowired
    public FollowRepository followRepository;

    @Autowired
    public UserRepository userRepository;

    public void followUser(Integer followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followerId = userRepository.findUserByUserName(userName).getId();

        FollowEntity follow = new FollowEntity();
        follow.setFollowerId(new UserEntity(followerId));
        follow.setFollowingId(new UserEntity(followingId));
        followRepository.save(follow);
    }

    @Transactional
    public void unFollowUser(Integer followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followerId = userRepository.findUserByUserName(userName).getId();

        followRepository.deleteByFollowerIdAndFollowingId(new UserEntity(followerId), new UserEntity(followingId));
    }

    public List<FollowerDTO> getFollowers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followingId = userRepository.findUserByUserName(userName).getId();

        List<FollowEntity> followers = followRepository.findByFollowingId(new UserEntity(followingId));

        return followers.stream()
                .map(follower -> {
                    UserEntity user = follower.getFollowerId();

                    List<PostDTO> posts = user.getPosts().stream()
                            .map(post -> new PostDTO(post.getId(), post.getCaption(), post.getUrl()))
                            .toList();

                    UserDetailsDTO userDetailsDTO = new UserDetailsDTO(
                            user.getUserName(),
                            user.getEmail(),
                            user.getProfilePic(),
                            user.getAbout(),
                            posts
                    );

                    return new FollowerDTO(follower.getId(), userDetailsDTO);
                })
                .toList();

    }

    public List<FollowingDTO> getFollowing() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followerId = userRepository.findUserByUserName(userName).getId();

        List<FollowEntity> following = followRepository.findByFollowerId(new UserEntity(followerId));

        return following.stream()
                .map(x -> {
                   UserEntity user = x.getFollowingId();

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

                   return new FollowingDTO(x.getId(), userDetailsDTO);
                })
                .toList();

    }

}