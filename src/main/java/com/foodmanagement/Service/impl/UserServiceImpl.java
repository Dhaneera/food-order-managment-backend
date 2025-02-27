package com.foodmanagement.Service.impl;


import com.foodmanagement.Entity.Role;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.RoleRepository;
import com.foodmanagement.Repository.UsersRepository;
import com.foodmanagement.Service.UsersService;
import com.foodmanagement.dto.GetUserByStatusDto;
import com.foodmanagement.dto.UsersDto;
import com.foodmanagement.dto.UpdatePasswordDto;
import com.foodmanagement.strategies.UserStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
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

    @Override
    public Page<User> searchUsersByUsername(String username, Pageable pageable) {
       return userRepository.findByUsernameContainingIgnoreCaseAndRoleNot(username,"ROLE_STAFF", pageable);
    }

    @Override
    public boolean updateStatus(Long id) {
        User user=getUserByName(id);
        if(ObjectUtils.isEmpty(user)){
            return false;
        }else{
            int b=userRepository.changeStatus(id,"ACTIVE");
            log.info(b>0?"updated":"not updated");
            return true;
        }
    }

    @Override
    public Page getAllStudents(Pageable pageable) {
        return userRepository.findAllBytatus("ROLE_PIRIVEN_STUDENT","ROLE_STUDENT",pageable);
    }

    @Override
    public Page<User> getAllEmployees(Pageable pageable) {
        return userRepository.findAllByRole("ROLE_STAFF",pageable);
    }

    @Override
    public Page<User> searchEmployeesByUsername(String username, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCaseAndRoleEmployee(username,"ROLE_PIRIVEN_STUDENT","ROLE_STUDENT",pageable);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    @Override
    public boolean updatePasswordByMail(UpdatePasswordDto updatePasswordDto) {
        Optional<User> userOptional = userRepository.findByMail(updatePasswordDto.getMail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Update password directly (without old password check)
            user.setPassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
            user.setUpdatedAt(java.time.LocalDateTime.now());
            userRepository.save(user);
            return true;
        }

        throw new IllegalStateException("User not found");
    }


    private User getUserByName(Long id){
        Optional<User> user= userRepository.findById(id);
        return user.orElseGet(User::new);
    }
}