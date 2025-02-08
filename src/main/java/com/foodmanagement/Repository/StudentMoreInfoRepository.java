package com.foodmanagement.Repository;

import com.foodmanagement.Entity.StudentMoreInfo;
import com.foodmanagement.dto.RegisterDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMoreInfoRepository extends CrudRepository<StudentMoreInfo,String> {

}
