package com.foodmanagement.Repository;

import com.foodmanagement.Entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {
    Page<Orders> findByStatus(String status, Pageable pageable);
    Optional<Orders> findById(String id);
    Page<Orders> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT orders_id, name, status, price, created_by, created_at, ordered_at FROM orders WHERE created_by = :created_by")
    Page<Orders> findByCreatedBy(@Param("created_by") String createdBy, Pageable pageable);

    @Query(value = """
       SELECT COUNT(*) AS row_count
       FROM meal m
       JOIN orders o ON m.order_id = o.orders_id
       WHERE DATE(o.ordered_at) = DATE(:orderedAt) AND m.type = :type
      """, nativeQuery = true)
    int findRowCountByTypeAndOrderedAt(
            @Param("orderedAt") String orderedAt,
            @Param("type") String type
    );
    @Query(value = """
       SELECT COUNT(*) AS row_count
       FROM meal m
       JOIN orders o ON m.order_id = o.orders_id
       WHERE DATE(o.ordered_at) = DATE(:orderedAt) AND m.type = :type AND m.status='Pending'
      """, nativeQuery = true)
    int findRowCountByTypeAndOrderedAtWhichIsPending(
            @Param("orderedAt") String orderedAt,
            @Param("type") String type
    );
    @Transactional
    @Modifying
    @Query("Update Orders o set status='COMPLETE' where DATE(o.orderedAt) = DATE(:orderedAt) ")
    void changeOrderStatusByDate(@Param("orderedAt") String orderedAt);
}


