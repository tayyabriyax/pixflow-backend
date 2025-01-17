package com.practice.pixflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {

    private Integer id;

    private PostDTO post;

    private UserDetailsDTO user;

    private Integer likesCount;

}
