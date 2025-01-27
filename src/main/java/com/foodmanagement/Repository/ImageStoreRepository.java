package com.foodmanagement.Repository;

import com.foodmanagement.Entity.ImageStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageStoreRepository extends JpaRepository<ImageStore,Long> {
}
