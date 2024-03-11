package com.example.tutorial5.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {
    private String email;
    private String firstName;
}
