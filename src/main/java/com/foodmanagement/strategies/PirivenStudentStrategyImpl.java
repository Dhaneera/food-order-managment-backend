package com.foodmanagement.strategies;

import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.RegisterDto;
import org.springframework.stereotype.Component;

@Component("piriven")
public class PirivenStudentStrategyImpl implements UserStrategies{
    @Override
    public CommonResponse<User> saveUser(RegisterDto registerDto) {

        return null;
    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void getUser() {

    }

    @Override
    public void getAllUsers() {

    }
}
