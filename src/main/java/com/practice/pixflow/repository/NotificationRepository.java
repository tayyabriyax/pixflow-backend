package com.practice.pixflow.repository;

import com.practice.pixflow.entity.NotificationEntity;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Integer> {
}
