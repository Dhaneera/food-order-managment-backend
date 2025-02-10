package com.foodmanagement.Service;



import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.GetUserByStatusDto;
import com.foodmanagement.dto.UsersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsersService {

    User addUser(UsersDto userDto);
    User updateUser(Long id, UsersDto userDto);
    boolean deleteUser(Long id);
    Page<User> getAllUsers(Pageable page);

    Page<User> getUserByUserType(GetUserByStatusDto dto, Pageable pageable);

    Page<User> searchUsersByUsername(String username, Pageable pageable);

    boolean updateStatus(Long id);

    Page getAllStudents(Pageable pageable);

    Page<User> getAllEmployees(Pageable pageable);

    Page<User> searchEmployeesByUsername(String username, Pageable pageable);


    Optional<User> getUserByUsername(String username);
}

