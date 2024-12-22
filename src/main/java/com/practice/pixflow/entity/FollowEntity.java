package com.practice.pixflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "follow")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private UserEntity followerId;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private UserEntity followingId;

    @CreationTimestamp
    @Column(name = "followed_at")
    private LocalDateTime followedAt;

}