package com.foodmanagement.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class UserNativeRepositoryImpl implements UserNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllUsersAccordingToRole(String role, String page){
        final String query= "SELECT us.name, us.username,us.status FROM users us JOIN user_roles ur ON ur.user_id = us.user_id JOIN roles rs ON rs.role_id = ur.role_id WHERE rs.name = ':role' LIMIT 10 OFFSET :offset";
        String queryToExecute = query.replace(":role",role).replace(":offset",page);
        return jdbcTemplate.queryForList(queryToExecute);
    }
}