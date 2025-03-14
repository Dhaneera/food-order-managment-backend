package com.foodmanagement.Repository;


import com.foodmanagement.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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

    @Query("SELECT u FROM User u JOIN u.roles r WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%')) AND r.name != :roleName AND r.name!= :roleName2")
    Page<User> findByUsernameContainingIgnoreCaseAndRoleEmployee(@Param("username") String username, @Param("roleName") String roleName,@Param("roleName2")String roleName2 , Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status where u.id=:id")
    int changeStatus(@Param("id") Long id,
                     @Param("status") String status);

//    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName and LIMIT 10 OFFSET 0")
//    Page<User> findAllByRole(@Param("roleName") String roleName,Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name IN( :roleName,:roleName2)")
    Page<User> findAllBytatus(@Param("roleName") String role1,@Param("roleName2") String role2,Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE u.mail = :mail")
    Optional<User> findByEmail(@Param("mail") String mail);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.mail=:mail WHERE u.id=:id")
    int changeMail(@Param("id") Long id,
                   @Param("mail") String mail);

    @Query("SELECT u FROM User u WHERE u.mail = :mail")
    Optional<User> findByMail(@Param("mail") String mail);

    @Modifying
    @Transactional
    @Query(value = "DELETE student_more_info, users FROM student_more_info INNER JOIN users ON student_more_info.user_id = users.user_id WHERE users.user_id = :id", nativeQuery = true)
    void deleteStudent(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles WHERE user_id = :id", nativeQuery = true)
    void deleteUserRoles(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE more_emp_info, users FROM more_emp_info INNER JOIN users ON more_emp_info.user_id = users.user_id WHERE users.user_id = :id", nativeQuery = true)
    void deleteEmployees(Long id);

    @Query(value = "SELECT us.name, us.mail, us.username, us.user_id\n" +
            "FROM users us\n" +
            "JOIN user_roles ur ON ur.user_id = us.user_id\n" +
            "JOIN roles rs ON rs.role_id = ur.role_id\n" +
            "WHERE rs.name = ':role'\n" +
            "LIMIT 10 OFFSET 0;",nativeQuery = true)
    Map findAllEmployees(@Param("role") String role);




//    @Modifying
//    @Transactional
//    @Query("DELETE more_emp_info,users using employee_more_info inner join on employee_more_info.user_id=users.user_id where user_id=:id")
//    void deleteEmployees(@Param("id") Long id);
}

