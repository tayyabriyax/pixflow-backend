package com.practice.pixflow.service;

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

    public void followUser(Integer followingId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followerId = userRepository.findUserByUserName(userName).getId();

        FollowEntity follow = new FollowEntity();
        follow.setFollowerId(new UserEntity(followerId));
        follow.setFollowingId(new UserEntity(followingId));
        followRepository.save(follow);
    }

    @Transactional
    public void unFollowUser(Integer followingId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followerId = userRepository.findUserByUserName(userName).getId();

        followRepository.deleteByFollowerIdAndFollowingId(new UserEntity(followerId), new UserEntity(followingId));
    }

    public List<FollowEntity> getFollowers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followingId = userRepository.findUserByUserName(userName).getId();

        return followRepository.findByFollowingId(new UserEntity(followingId));
    }

    public List<FollowEntity> getFollowing(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followerId = userRepository.findUserByUserName(userName).getId();

        return followRepository.findByFollowerId(new UserEntity(followerId));
    }

}