package com.foodmanagement.Service;

import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.RegisterDto;

public interface AuthService {
    public String login(String username, String password);
    public CommonResponse register(RegisterDto registerDto);
    public String logout(String username);
    public String resetPassword(String username, String password);
    public String changePassword(String username, String password);
    public String changeRole(String username, String role);
    public String changeStatus(String username, String status);
    public String changeProfilePicture(String username, String profilePicture);
}
