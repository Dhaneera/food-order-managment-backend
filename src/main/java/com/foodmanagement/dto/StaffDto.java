package com.foodmanagement.dto;

import jakarta.persistence.Id;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffDto {
    @Id
    private Long id;
    private String name;
    private String profileImage;
    private Long roleId; // Assuming Role has an ID.

    private List<OrdersDto> orders;
}
