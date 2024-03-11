package com.example.tutorial5.response;

import com.example.tutorial5.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllUsersResponse {

    private String message;
    private Boolean success;
    private List<User> users;

}
