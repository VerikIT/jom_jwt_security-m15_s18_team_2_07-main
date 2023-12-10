package com.softserve.itacademy.todolist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    @JsonCreator
    public UserRequest(@JsonProperty("firstName") @NotNull String firstName,
                       @JsonProperty("lastName") @NotNull String lastName,
                       @JsonProperty("email") @NotNull String email,
                       @JsonProperty("password") String password,
                       @JsonProperty("role") @NotNull String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}

