package com.practice.pixflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingDTO {

    private Integer id;

    private UserDetailsDTO followingId;

}
