package com.softserve.itacademy.todolist.dto;

import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Getter
@Setter
public class ToDoResponseDto {
    Long id;
    String title;
    LocalDateTime createdAt;
    Long ownerId;
    List<Long> collaboratorIds;

    public ToDoResponseDto(ToDo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.createdAt = todo.getCreatedAt();
        this.ownerId = todo.getOwner().getId();
        this.collaboratorIds = todo.getCollaborators().stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

}




