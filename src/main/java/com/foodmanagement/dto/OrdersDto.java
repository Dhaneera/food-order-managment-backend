package com.foodmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class OrdersDto {
    @Valid
    private Long id;
    private String name;
    private String status;
    private Double price;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime orderedAt;

    private Long studentId;
    private Long pirivenStudentId;
    private Long staffId;
}
