package com.foodmanagement.Service;



import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.GetUserByStatusDto;
import com.foodmanagement.dto.PaginatedResponse;
import com.foodmanagement.dto.UsersDto;
import com.foodmanagement.dto.UpdatePasswordDto;
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

    PaginatedResponse<?> getAllEmployees(String role, String pageNum);
    PaginatedResponse<?> getAllStudents(String role, String pageNum);

    Page<User> searchEmployeesByUsername(String username, Pageable pageable);


    Optional<User> getUserByUsername(String username);

    boolean updatePasswordByMail(UpdatePasswordDto updatePasswordDto);
     void updateUserContactInfo(Long userId, String phoneNumber, String mail);

}

