package com.foodmanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "image_store")
public class ImageStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageStoreId")
    private Long id;
    private String filename;
    private String fileType;

    @Lob
    private byte[] fileData;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name ="userId")
    private User user;


}
