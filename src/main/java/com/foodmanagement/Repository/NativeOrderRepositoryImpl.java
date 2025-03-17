package com.foodmanagement.Repository;

import com.foodmanagement.dto.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.foodmanagement.Repository.UserNativeRepositoryImpl.getPaginatedResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class NativeOrderRepositoryImpl implements NativeOrderRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginatedResponse<?> getAllOrders(String createdBy, int pageNum) {

        try {
            String sql = "SELECT orders_id as id, name, status, price, created_by as createdBy, created_at, ordered_at as orderedAt FROM orders WHERE created_by = :created_by LIMIT :size OFFSET :offset";
            if (createdBy==null || createdBy.isBlank() || createdBy.equalsIgnoreCase("all")) {
                sql = sql.replace("WHERE created_by = :created_by", "");
                sql = sql.replace(":size", "10").replace(":offset", String.valueOf(pageNum));
            }else {
                sql = sql.replace(":created_by", createdBy).replace(":size", "5").replace(":offset", String.valueOf(pageNum));
            }
            List<Map<String, Object>> queryList = jdbcTemplate.queryForList(sql);
            log.info(queryList.toString());

            return getPaginatedResponse(queryList);

        }catch (Exception exc){
            throw new RuntimeException(exc);
        }

    }
}
