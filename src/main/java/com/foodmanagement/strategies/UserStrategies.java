package com.foodmanagement.strategies;

import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.RegisterDto;
import com.foodmanagement.dto.StudentMoreInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserStrategies {
    public CommonResponse<User> saveUser(RegisterDto registerDto);

    public CommonResponse saveMoreDetails(StudentMoreInfoDto studentMoreInfoDto);

    public void updateUser();
    public void deleteUser();
    public void getUser();
    public Page<User> getAllUsers(String status, Pageable pageable);
}
