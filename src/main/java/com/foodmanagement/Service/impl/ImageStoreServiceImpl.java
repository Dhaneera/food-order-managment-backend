package com.foodmanagement.Service.impl;

import com.foodmanagement.Entity.ImageStore;
import com.foodmanagement.Entity.User;
import com.foodmanagement.Repository.ImageStoreRepository;
import com.foodmanagement.Service.ImageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
public class ImageStoreServiceImpl implements ImageStoreService {
    @Autowired
    ImageStoreRepository imageStoreRepository;


    @Override
    public ImageStore saveImage(MultipartFile file, long id) throws IOException {
        ImageStore image = new ImageStore();
        image.setFilename(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        User user = new User();
        user.setId(id);
        byte [] encodedString = Base64.getEncoder().encode(file.getBytes());
        image.setFileData(encodedString);
        image.setUser(user);
        Optional<ImageStore> imageStore=imageStoreRepository.findByUserId(id);

        if(ObjectUtils.isEmpty(imageStore)){
            return imageStoreRepository.save(image);
        }else {
            imageStoreRepository.deleteByUserId(id);
            return imageStoreRepository.save(image);
        }
    }

    @Override
    public Optional<ImageStore> getImageById(Long id) {
        return imageStoreRepository.findById(id);
    }

    public byte[] getImageBase64ById(Long id) {
        Optional<ImageStore> imageOptional = imageStoreRepository.findByUserId(id);

        if (imageOptional.isPresent()) {
            ImageStore image = imageOptional.get();
            // Encode the image data in Base64
//            return Base64.getEncoder().encodeToString(image.getFileData());
            return Base64.getDecoder().decode(image.getFileData());
        }

        return null;
    }

}
