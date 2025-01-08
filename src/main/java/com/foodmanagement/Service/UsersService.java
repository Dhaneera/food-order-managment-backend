package com.foodmanagement.Service;



import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.UsersDto;

import java.util.List;

public interface UsersService {

    User addUser(UsersDto userDto);
    User updateUser(Long id, UsersDto userDto);
    boolean deleteUser(Long id);
    List<UsersDto> getAllUsers();

}

