package com.foodmanagement.Repository;

import com.foodmanagement.Entity.MoreEmpInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoreEmpInfoRepository extends CrudRepository<MoreEmpInfo, String> {
}
