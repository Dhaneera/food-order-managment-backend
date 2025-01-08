package com.foodmanagement.strategies;

import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.RegisterDto;

public interface UserStrategies {
    public CommonResponse<User> saveUser(RegisterDto registerDto);
    public void updateUser();
    public void deleteUser();
    public void getUser();
    public void getAllUsers();
}
