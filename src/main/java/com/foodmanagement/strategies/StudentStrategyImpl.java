package com.foodmanagement.strategies;

import com.foodmanagement.Entity.Role;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.RoleRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.dto.CommonResponse;
import com.foodmanagement.dto.RegisterDto;
import com.foodmanagement.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Component("student")
@RequiredArgsConstructor
public class StudentStrategyImpl implements UserStrategies {

    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;
    @Override
    public CommonResponse<User> saveUser(RegisterDto registerDto) {
        if(usersRepository.existsByUsername(registerDto.getPhoneNumber())){
            return new CommonResponse<User>(0001,"User already exists",null);
        }
        Role role = roleRepository.findByName("ROLE_STUDENT");
        if(ObjectUtils.isEmpty(role)){
            role = new Role();
            role.setName("ROLE_STUDENT");
            roleRepository.save(role);
        }
        User user = new User();
        user.setUsername(registerDto.getPhoneNumber());
        user.setName(registerDto.getName());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy(registerDto.getCreatedBy());
        user.setMail(registerDto.getMail());
        user.setPassword(registerDto.getPassword());
        user.setStatus("ACTIVE");

        usersRepository.save(user);
        return new CommonResponse<User>(0000,"Successful",user);
    }

    @Override
    public void updateUser() {
        System.out.println("Student updated");
    }

    @Override
    public void deleteUser() {
        System.out.println("Student deleted");
    }

    @Override
    public void getUser() {
        System.out.println("Student retrieved");
    }

    @Override
    public void getAllUsers() {
        System.out.println("All students retrieved");
    }
}
