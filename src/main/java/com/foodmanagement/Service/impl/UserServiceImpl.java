package com.foodmanagement.Service.impl;


import com.foodmanagement.Entity.Role;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.RoleRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.Service.UsersService;
import com.foodmanagement.dto.GetUserByStatusDto;
import com.foodmanagement.dto.UsersDto;
import com.foodmanagement.strategies.UserStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UsersService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final Map<String, UserStrategies> userStrategiesHashMap;


    public UserServiceImpl(UsersRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, Map<String, UserStrategies> userStrategiesHashMap) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userStrategiesHashMap = userStrategiesHashMap;
    }

    @Override
    public User addUser(UsersDto userDto) {
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            throw new RuntimeException("Role USER not found");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setMail(userDto.getMail());
        user.setStatus("ACTIVE");
        user.setCreatedBy("System");
        user.setUpdatedBy("System");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        return userRepository.save(user);
    }


    @Override
    public User updateUser(Long id, UsersDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    @Override
    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<User> getAllUsers(Pageable page) {
         return userRepository.findAll(page);
    }

    @Override
    public Page<User> getUserByUserType(GetUserByStatusDto dto, Pageable pageable) {
        if(userStrategiesHashMap.containsKey(dto.getType())) {
            UserStrategies userStrategies = userStrategiesHashMap.get(dto.getType());
            return userStrategies.getAllUsers(dto.getStatus(), pageable);
        }
        throw new RuntimeException("User type not found");
    }




}