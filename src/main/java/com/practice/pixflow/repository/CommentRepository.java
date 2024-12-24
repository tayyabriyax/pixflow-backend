package com.practice.pixflow.repository;

import com.practice.pixflow.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {
}
