package com.foodmanagement.Service.impl;

import com.foodmanagement.Service.AuthService;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.EmployeeMoreInfoDto;
import com.foodmanagement.dto.RegisterDto;
import com.foodmanagement.dto.StudentMoreInfoDto;
import com.foodmanagement.strategies.UserStrategies;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final HashMap<String, UserStrategies> userStrategiesHashMap;

    public AuthServiceImpl(Map<String, UserStrategies> strategiesMap) {
        this.userStrategiesHashMap = new HashMap<>(strategiesMap);
    }
    @Override
    public String login(String username, String password) {
        return "";
    }

    @Override
    public CommonResponse register(RegisterDto registerDto) {
        UserStrategies userStrategies=userStrategiesHashMap.get(registerDto.getRoleType().toLowerCase());
        return userStrategies.saveUser(registerDto);
    }

    @Override
    public CommonResponse saveMoreInfo(StudentMoreInfoDto studentMoreInfoDto){
        UserStrategies userStrategies=userStrategiesHashMap.get("student");
        return userStrategies.saveMoreDetails(studentMoreInfoDto);
    }

    @Override
    public String logout(String username) {
        return "";
    }

    @Override
    public String resetPassword(String username, String password) {
        return "";
    }

    @Override
    public String changePassword(String username, String password) {
        return "";
    }

    @Override
    public String changeRole(String username, String role) {
        return "";
    }

    @Override
    public String changeStatus(String username, String status) {
        return "";
    }

    @Override
    public String changeProfilePicture(String username, String profilePicture) {
        return "";
    }

    @Override
    public CommonResponse saveMoreInfoEmployee(EmployeeMoreInfoDto employeeMoreInfoDto) {
        UserStrategies userStrategies=userStrategiesHashMap.get("staff");
        return userStrategies.saveMoreDetailsEmp(employeeMoreInfoDto);
    }
}
