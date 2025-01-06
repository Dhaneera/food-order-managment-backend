package com.foodmanagement.dto;


import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class LoginDto {
    private  String username;
    private String password;
}

