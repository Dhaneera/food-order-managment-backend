package com.foodmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_more_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentMoreInfo {

    @Id
    private String studentId;
    private String mail;
    private String faculty;
    private String gender;
    private String batch;
    private String stream;

    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;
}
