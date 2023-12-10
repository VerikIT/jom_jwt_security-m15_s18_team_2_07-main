package com.softserve.itacademy.todolist.dto;

import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class ToDoTransformer {

    public static ToDo transformFromRequestToModel(ToDoRequestDto todoRequest, UserService userService) {
        ToDo todo = new ToDo();
        todo.setTitle(todoRequest.getTitle());
        todo.setCreatedAt(todoRequest.getCreatedAt());

        User owner = userService.readById(todoRequest.getOwnerId());
        todo.setOwner(owner);
        List<User> collaborators = new ArrayList<>();
        if (todoRequest.getCollaboratorIds() != null) {
            for (Long collaboratorId : todoRequest.getCollaboratorIds()) {
                User collaborator = userService.readById(collaboratorId);
                collaborators.add(collaborator);
            }
        }
        todo.setCollaborators(collaborators);
        return todo;
    }

    public static ToDoDTO mapToDTO(ToDo todo) {
        ToDoDTO dto = new ToDoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setCreatedAt(todo.getCreatedAt());
        // Добавьте другие поля, если необходимо
        return dto;
    }
}