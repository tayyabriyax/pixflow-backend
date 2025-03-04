package com.practice.pixflow.dto;

import com.practice.pixflow.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private Integer id;

    private String userName;

    private String email;

    private String profilePic;

    private String about;

    private List<PostDTO> posts;

    public UserDetailsDTO(Integer id,
                          String userName,
                          String email,
                          String profilePic,
                          String about) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.profilePic = profilePic;
        this.about = about;
    }

}
