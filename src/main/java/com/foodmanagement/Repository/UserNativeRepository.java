package com.foodmanagement.Repository;

import java.util.List;
import java.util.Map;

public interface UserNativeRepository {

    List<Map<String, Object>> getAllUsersAccordingToRole(String role, String page);
}
