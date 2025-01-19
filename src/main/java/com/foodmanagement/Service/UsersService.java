package com.foodmanagement.Service;



import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.GetUserByStatusDto;
import com.foodmanagement.dto.UsersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {

    User addUser(UsersDto userDto);
    User updateUser(Long id, UsersDto userDto);
    boolean deleteUser(Long id);
    List<UsersDto> getAllUsers();

    Page<User> getUserByUserType(GetUserByStatusDto dto, Pageable pageable);
}

