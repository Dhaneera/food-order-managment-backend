package com.foodmanagement.Repository;

import com.foodmanagement.Entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders,UUID> {
    Page<Orders> findByStatus(String status, Pageable pageable);
    Optional<Orders> findById(UUID id);
    Page<Orders> findAll(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT orders_id, name, status, price, created_by, created_at, ordered_at FROM orders WHERE created_by = :created_by")
    Page<Orders> findByCreatedBy(@Param("created_by") String createdBy, Pageable pageable);

}
