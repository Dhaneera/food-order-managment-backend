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
public class EmployeeMoreInfoDto {
    private String nic;
    private String email;
    private String department;
    private String gender;
    private boolean isInternal;
    private long userId;
}
