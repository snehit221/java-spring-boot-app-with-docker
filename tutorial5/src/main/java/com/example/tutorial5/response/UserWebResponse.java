package com.example.tutorial5.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWebResponse {

    private String message;
    private Boolean success;
}
