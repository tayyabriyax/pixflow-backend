package com.practice.pixflow.repository;

import com.practice.pixflow.entity.BookmarkEntity;
import com.practice.pixflow.entity.PostEntity;
import com.practice.pixflow.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookmarkRepository extends CrudRepository<BookmarkEntity, Integer> {

    boolean existsByUserAndPost(UserEntity user, PostEntity post);

    void deleteByUserAndPost(UserEntity user, PostEntity post);

    List<BookmarkEntity> findByUser(UserEntity user);

}
