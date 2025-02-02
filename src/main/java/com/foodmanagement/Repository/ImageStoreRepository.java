package com.foodmanagement.Repository;

import com.foodmanagement.Entity.ImageStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ImageStoreRepository extends JpaRepository<ImageStore,Long> {
    Optional<ImageStore> findByUserId(long id);
    @Modifying
    @Transactional
    @Query("delete from ImageStore i where i.user.id = ?1")
    int deleteByUserId(long id);
}