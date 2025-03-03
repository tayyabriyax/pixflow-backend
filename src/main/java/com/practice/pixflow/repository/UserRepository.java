package com.practice.pixflow.repository;

import com.practice.pixflow.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findUserByUserName(String userName);

    List<UserEntity> findAll();

}