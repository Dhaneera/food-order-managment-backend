package com.foodmanagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PirivenStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PirivenStudentId")
    private Long id;
    private String name;
    private String phoneNumber;
    private String mail;
    private String status;
    private String createdBy;
    private String updatedBy ;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "pirivenStudent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orders> orders;
}
