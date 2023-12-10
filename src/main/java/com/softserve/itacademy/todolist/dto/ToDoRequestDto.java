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
public class ToDoRequestDto {
    private Long id;

    @NotBlank(message = "The 'title' cannot be empty")
    private String title;

    private LocalDateTime createdAt;

    private Long ownerId;
    private List<Long> collaboratorIds;
    private List<TaskRequest> tasks;
    private List<UserResponse> users;


    public ToDoRequestDto(Long id, String title, LocalDateTime createdAt, Long ownerId, List<Long> collaboratorIds,
                          List<TaskRequest> tasks, List<UserResponse> users) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.ownerId = ownerId;
        this.collaboratorIds = collaboratorIds;
        this.tasks = tasks;
        this.users = users;
    }
}