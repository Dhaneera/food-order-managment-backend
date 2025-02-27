package com.foodmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "more_emp_info")
@Getter
@Setter
public class MoreEmpInfo {
    @Id
    private String nic;
    private String mail;
    private String department;
    private String gender;
    private boolean isInternal;

    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
