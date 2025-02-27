package com.foodmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class UpdatePasswordDto {
    @NotBlank(message = "mail cannot be empty")
    private String mail;
    @NotBlank(message = "New password cannot be empty")
    private String newPassword;

}
