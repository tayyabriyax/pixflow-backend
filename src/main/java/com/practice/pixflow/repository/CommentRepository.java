package com.practice.pixflow.repository;

import com.practice.pixflow.entity.CommentEntity;
import com.practice.pixflow.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {

    List<CommentEntity> findByPostId(PostEntity id);

}
