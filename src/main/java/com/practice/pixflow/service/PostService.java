package com.practice.pixflow.service;

import com.practice.pixflow.dto.CreatePostDTO;
import com.practice.pixflow.dto.EditPostDTO;
import com.practice.pixflow.entity.PostEntity;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.PostRepository;
import com.practice.pixflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@Component
public class PostService {

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public UserRepository userRepository;

    public void createPost(CreatePostDTO post, MultipartFile postFile){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            String fileName = postFile.getOriginalFilename();
            String absolutePath = new File("src/main/resources/static/upload/").getAbsolutePath();
            String filePath = absolutePath + "/" + fileName;
            postFile.transferTo(new File(filePath));

            PostEntity newPost = new PostEntity();
            newPost.setCaption(post.getCaption());
            newPost.setUrl(filePath);

            existedUser.getPosts().add(newPost);
            newPost.setUser(existedUser);

            postRepository.save(newPost);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePost(EditPostDTO post, MultipartFile postFile){
        try{
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
            newPost.setUrl(filePath);

            existedUser.getPosts().forEach(x -> {
                if(Objects.equals(x.getId(), newPost.getId())){
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

    public void removePost(Integer id){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            UserEntity existedUser = userRepository.findUserByUserName(userName);

            existedUser.getPosts().removeIf(x -> x.getId().equals(id));

            userRepository.save(existedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
