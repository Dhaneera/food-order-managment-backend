package com.foodmanagement.Repository;

import com.foodmanagement.Entity.StudentMoreInfo;
import com.foodmanagement.dto.RegisterDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMoreInfoRepository extends CrudRepository<StudentMoreInfo,String> {
    @Query(value = "SELECT COUNT(*) FROM student_more_info WHERE USER_ID=:id" ,nativeQuery = true)
    int getStudentCount(@Param("id") Long id);

}
