package com.practice.pixflow.repository;

import com.practice.pixflow.entity.FollowEntity;
import com.practice.pixflow.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowRepository extends CrudRepository<FollowEntity, Integer> {

    List<FollowEntity> findByFollowerId(UserEntity id);

    List<FollowEntity> findByFollowingId(UserEntity id);

    void deleteByFollowerIdAndFollowingId(UserEntity followerId, UserEntity followingId);

}