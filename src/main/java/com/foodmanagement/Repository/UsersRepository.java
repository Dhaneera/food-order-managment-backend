package com.foodmanagement.Repository;


import com.foodmanagement.Entity.Orders;
import com.foodmanagement.Entity.User;
import com.foodmanagement.dto.UsersDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
    Optional<User>findByUsername(String username);
    Boolean existsByUsername(String username);
    Page<User> findAll(Pageable pageable);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.status = :status AND r.name = :roleName")
    Page<User> findAllByStatusAndRole(@Param("status") String status,
                                      @Param("roleName") String roleName,
                                      Pageable pageable);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%')) AND r.name != :roleName")
    Page<User> findByUsernameContainingIgnoreCaseAndRoleNot(@Param("username") String username, @Param("roleName") String roleName, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status where u.id=:id")
    int changeStatus(@Param("id") Long id,
                         @Param("status") String status);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name IN( :roleName,:roleName2)")
    Page<User> findAllByStatus(@Param("roleName") String role1,@Param("roleName2") String role2,Pageable pageable);
}

