package com.foodmanagement.Service;

import com.foodmanagement.Entity.ImageStore;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


public interface ImageStoreService  {
    public ImageStore saveImage(MultipartFile file, long id) throws IOException;
    public Optional<ImageStore> getImageById(Long id);
    public byte[] getImageBase64ById(Long id);


}
