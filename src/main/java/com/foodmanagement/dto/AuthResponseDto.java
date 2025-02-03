package com.foodmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType="Bearer";
    private List<String> roles;
    private Long userId;
    private Long roleId;
    private String status;

    public AuthResponseDto(String accessToken, String refreshToken, List<String> roles, Long userId, Long roleId,String status) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;
        this.userId = userId;
        this.roleId = roleId;
        this.status=status;

    }

    public AuthResponseDto(String accessToken, String refreshToken, List<String> roles, Long userId, Long roleId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;
        this.userId = userId;
        this.roleId = roleId;
    }
}
