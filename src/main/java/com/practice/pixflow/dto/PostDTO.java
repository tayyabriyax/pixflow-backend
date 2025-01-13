package com.practice.pixflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Integer id;

    private String caption;

    private String url;

    private UserDetailsDTO auther;

    public PostDTO(Integer id, String caption, String url) {
        this.id = id;
        this.caption = caption;
        this.url = url;
    }

}
