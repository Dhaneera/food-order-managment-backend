package com.foodmanagement.Repository;

import com.foodmanagement.dto.PaginatedResponse;

public interface UserNativeRepository {

    PaginatedResponse<?> getAllUsersAccordingToRole(String role, String page, String size);
}
