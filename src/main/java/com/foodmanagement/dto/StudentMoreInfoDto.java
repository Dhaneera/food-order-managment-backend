package com.foodmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentMoreInfoDto {
    private long userId;
    private String studentId;
    private String faculty;
    private String gender;
    private String batch;
    private String stream;
}
