package com.softserve.itacademy.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ToDoDTO {
    private Long id;

    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    private LocalDateTime createdAt;

    private Long ownerId;

    private List<Long> collaboratorIds;
}