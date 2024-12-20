package com.practice.pixflow.repository;

import com.practice.pixflow.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {
}
