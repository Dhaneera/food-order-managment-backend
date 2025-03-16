package com.foodmanagement.Repository;

import com.foodmanagement.dto.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserNativeRepositoryImpl implements UserNativeRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaginatedResponse<?> getAllUsersAccordingToRole(String role, String page, String size){
        final String query= "SELECT us.name, us.username,us.status,us.user_id as id FROM users us JOIN user_roles ur ON ur.user_id = us.user_id JOIN roles rs ON rs.role_id = ur.role_id WHERE rs.name = ':role' LIMIT :size OFFSET :offset";
        String queryToExecute = query.replace(":role",role).replace(":offset",page).replace(":size",size);

        log.info("query executed to retrive employees {}",queryToExecute);
        List<Map<String,Object>> mapListOfAllEmployeeData=jdbcTemplate.queryForList(queryToExecute);
        PaginatedResponse<?> response=new PaginatedResponse<>();
        List listOfContent = new ArrayList();
        mapListOfAllEmployeeData.stream().forEach((objectMap)->{
            listOfContent.add(objectMap);
        });
        response.setContent(listOfContent);
        response.setSize(listOfContent.size());
        return response;
    }
}