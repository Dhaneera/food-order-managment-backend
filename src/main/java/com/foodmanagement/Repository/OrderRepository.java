package com.foodmanagement.Repository;

import com.foodmanagement.Entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders,UUID> {
    Page<Orders> findByStatus(String status, Pageable pageable);
    Optional<Orders> findById(UUID id);
}
