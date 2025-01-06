package com.foodmanagement.strategies;

import com.foodmanagement.Entity.Role;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.RoleRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collections;

@Component("staff")
@RequiredArgsConstructor
public class StaffStrategyImpl implements UserStrategies {

    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;

    @Override
    public CommonResponse<User> saveUser(RegisterDto registerDto) {
        if(Boolean.TRUE.equals(usersRepository.existsByUsername(registerDto.getPhoneNumber()))){
            return new CommonResponse<User>(0001,"User already exists",null);
        }
        Role role = roleRepository.findByName("ROLE_STAFF");
        if(ObjectUtils.isEmpty(role)){
            role = new Role();
            role.setName("ROLE_STAFF");
            roleRepository.save(role);
        }
        User user = new User();
        user.setUsername(registerDto.getPhoneNumber());
        user.setName(registerDto.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(registerDto.getCreatedBy());
        user.setMail(registerDto.getMail());
        user.setPassword(registerDto.getPassword());
        user.setStatus("PENDING");
        user.setRoles(Collections.singletonList(role));


        usersRepository.save(user);
        return new CommonResponse<User>(0000,"Successful",user);
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
