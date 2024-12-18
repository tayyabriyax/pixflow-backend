package com.practice.pixflow.repository;

import com.practice.pixflow.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    UserEntity findUserByUserName(String userName);

}