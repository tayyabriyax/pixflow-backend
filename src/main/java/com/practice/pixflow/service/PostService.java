package com.practice.pixflow.service;

import com.practice.pixflow.dto.*;
import com.practice.pixflow.entity.FollowEntity;
import com.practice.pixflow.entity.PostEntity;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.FollowRepository;
import com.practice.pixflow.repository.PostRepository;
import com.practice.pixflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public FollowRepository followRepository;

    public void createPost(CreatePostDTO post, MultipartFile postFile) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFileName = postFile.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + "_" + originalFileName;

            String filePath = uploadDir + uniqueFileName;
            postFile.transferTo(new File(filePath));

            PostEntity newPost = new PostEntity();
            newPost.setCaption(post.getCaption());
            newPost.setUrl("/upload/" + uniqueFileName); // frontend access

            existedUser.getPosts().add(newPost);
            newPost.setUser(existedUser);

            postRepository.save(newPost);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void updatePost(EditPostDTO post, MultipartFile postFile) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            String fileName = postFile.getOriginalFilename();
            String absolutePath = new File("src/main/resources/static/upload/").getAbsolutePath();
            String filePath = absolutePath + "/" + fileName;
            postFile.transferTo(new File(filePath));

            PostEntity newPost = new PostEntity();
            newPost.setId(post.getId());
            newPost.setCaption(post.getCaption());
            newPost.setUrl("/upload/" + fileName);

            existedUser.getPosts().forEach(x -> {
                if (Objects.equals(x.getId(), newPost.getId())) {
                    x.setCaption(newPost.getCaption());
                    x.setUrl(newPost.getUrl());
                }
            });
            newPost.setUser(existedUser);

            postRepository.save(newPost);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removePost(Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            existedUser.getPosts().removeIf(x -> x.getId().equals(id));

            userRepository.save(existedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<PostDTO> getPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Integer followerId = userRepository.findUserByUserName(userName).getId();

        List<FollowEntity> followingList = followRepository.findByFollowerId(new UserEntity(followerId));

        return followingList.stream()
                .flatMap(following -> {
                    UserEntity userEntity = following.getFollowingId();
                    return userEntity.getPosts().stream()
                            .map(post -> new PostDTO(post.getId(), post.getCaption(), post.getUrl(),
                                    new UserDetailsDTO(
                                            post.getUser().getId(),
                                            post.getUser().getUserName(),
                                            post.getUser().getEmail(),
                                            post.getUser().getProfilePic(),
                                            post.getUser().getAbout())));
                })
                .toList();
    }

}
