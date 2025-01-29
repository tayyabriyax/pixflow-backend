package com.practice.pixflow.service;

import com.practice.pixflow.dto.BookmarkDTO;
import com.practice.pixflow.dto.PostDTO;
import com.practice.pixflow.dto.UserDetailsDTO;
import com.practice.pixflow.entity.BookmarkEntity;
import com.practice.pixflow.entity.PostEntity;
import com.practice.pixflow.entity.UserEntity;
import com.practice.pixflow.repository.BookmarkRepository;
import com.practice.pixflow.repository.PostRepository;
import com.practice.pixflow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookmarkService {

    @Autowired
    public BookmarkRepository bookmarkRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    @Transactional
    public void addBookmark(Integer postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userRepository.findUserByUserName(userName);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));;

        if(!bookmarkRepository.existsByUserAndPost(user, post)){
            BookmarkEntity bookmark = new BookmarkEntity();
            bookmark.setUser(user);
            bookmark.setPost(post);
            bookmarkRepository.save(bookmark);
        }
    }

    @Transactional
    public void removeBookmark(Integer postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userRepository.findUserByUserName(userName);
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        bookmarkRepository.deleteByUserAndPost(user, post);
    }

    @Transactional
    public List<BookmarkDTO> getBookmark(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userRepository.findUserByUserName(userName);

        List<BookmarkEntity> bookmark = bookmarkRepository.findByUser(user);

        return bookmark.stream().map(x -> {
            BookmarkDTO bookmarkDTO = new BookmarkDTO();

            bookmarkDTO.setId(x.getId());
            bookmarkDTO.setPost(new PostDTO(
                    x.getPost().getId(),
                    x.getPost().getCaption(),
                    x.getPost().getUrl()
                    ));
            bookmarkDTO.setUser(new UserDetailsDTO(
                    x.getUser().getUserName(),
                    x.getUser().getEmail(),
                    x.getUser().getProfilePic(),
                    x.getUser().getAbout()
            ));
            return bookmarkDTO;
        }).toList();
    }
}
