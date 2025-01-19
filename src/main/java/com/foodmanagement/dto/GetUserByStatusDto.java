package com.foodmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserByStatusDto {
    private String type;
    private String  status;
}
