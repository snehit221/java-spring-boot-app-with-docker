package com.example.tutorial5.response;

import com.example.tutorial5.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

    private Boolean success;
    private User user;

}
