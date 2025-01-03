package com.foodmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class UsersDto {

    @Valid
    private Long id;
    private String username;
    private String password;
    private List<String> roles;

    public @Valid Long getId() {
        return id;
    }

    public void setId(@Valid Long id) {
        this.id = id;
    }

}
