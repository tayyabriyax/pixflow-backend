package com.practice.pixflow.repository;

import com.practice.pixflow.entity.LikeEntity;
import com.practice.pixflow.entity.PostEntity;
import com.practice.pixflow.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<LikeEntity, Integer> {

    boolean existsByUserIdAndPostId(UserEntity user, PostEntity post);

    void deleteByUserIdAndPostId(UserEntity user, PostEntity post);

    Integer countByPostId(PostEntity post);

    List<LikeEntity> findByPostId(PostEntity post);

}
