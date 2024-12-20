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

    private String userName;

    private String email;

    private String profilePic;

    private String about;

    private List<PostDTO> posts;

}
