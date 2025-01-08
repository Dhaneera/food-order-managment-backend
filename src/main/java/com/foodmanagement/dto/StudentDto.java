    package com.foodmanagement.dto;

    import jakarta.validation.Valid;
    import lombok.*;
    import java.time.LocalDateTime;
    import java.util.List;


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class StudentDto {
        @Valid
        private Long id;
        private String name;
        private String phoneNumber;
        private String mail;
        private String status;
        private String createdBy;
        private String updatedBy;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        private List<OrdersDto> orders;
    }
