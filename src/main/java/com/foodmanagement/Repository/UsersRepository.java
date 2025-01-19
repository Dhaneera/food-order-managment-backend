package com.foodmanagement.Repository;


import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.UsersDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User,Long> {
    Optional<User>findByUsername(String username);
    Boolean existsByUsername(String username);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.status = :status AND r.name = :roleName")
    Page<User> findAllByStatusAndRole(@Param("status") String status,
                                      @Param("roleName") String roleName,
                                      Pageable pageable);


}

