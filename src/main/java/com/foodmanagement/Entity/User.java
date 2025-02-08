package com.foodmanagement.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;
    private String phoneNumber;
    private String mail;
    private String status;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "userId",referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId",referencedColumnName = "roleId"))
    private List<Role>roles= new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private ImageStore imageStore;

    @OneToOne(mappedBy = "user")
    private StudentMoreInfo studentMoreInfo;

    @OneToOne(mappedBy = "user")
    private MoreEmpInfo moreEmpInfo;
}
