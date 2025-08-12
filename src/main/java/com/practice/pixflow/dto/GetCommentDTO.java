package com.practice.pixflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentDTO {

    private String content;

    private UserDetailsDTO userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
