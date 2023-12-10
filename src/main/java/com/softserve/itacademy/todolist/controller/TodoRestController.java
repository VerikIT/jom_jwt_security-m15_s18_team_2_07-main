package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.*;
import com.softserve.itacademy.todolist.model.ToDo;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.ToDoService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users/{u_id}/todos")
@RequiredArgsConstructor
public class TodoRestController {

    private final ToDoService toDoService;
    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ToDoResponseDto getTodo(@PathVariable("id") long todoId) {
        ToDo todo = toDoService.readById(todoId);
        if (todo != null) {
            return new ToDoResponseDto(todo);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found");
        }
    }


    @GetMapping
    @ResponseStatus(OK)
    public List<ToDoResponseDto> getAll(@PathVariable("u_id") long ownerId) {
        List<ToDoResponseDto> todoDTOs = new ArrayList<>();
        List<ToDo> todos = toDoService.readById(ownerId).getOwner().getMyTodos();
        for (ToDo todo : todos) {
            todoDTOs.add(new ToDoResponseDto(todo));
        }
        return todoDTOs;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<ToDoDTO> create(@Validated @RequestBody ToDoRequestDto todoRequest,
                                          @PathVariable("u_id") long ownerId) {
        User owner = userService.readById(ownerId);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        ToDo todo = ToDoTransformer.transformFromRequestToModel(todoRequest, userService);
        todo.setOwner(owner);
        ToDo createdTodo = toDoService.create(todo);
        if (createdTodo != null) {
            ToDoDTO createdTodoDTO = ToDoTransformer.mapToDTO(createdTodo);
            return ResponseEntity.ok(createdTodoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{todo_id}")
    public ResponseEntity<ToDoDTO> updateToDo(@Validated @RequestBody ToDoRequestDto todoRequest,
                                              @PathVariable("todo_id") long todoId) {
        ToDo existingToDo = toDoService.readById(todoId);
        if (existingToDo != null) {
            existingToDo.setTitle(todoRequest.getTitle());
            ToDo updatedToDo = toDoService.update(existingToDo);
            ToDoDTO updatedToDoDTO = ToDoTransformer.mapToDTO(updatedToDo);
            return ResponseEntity.ok(updatedToDoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable("id") long todoId) {
        ToDo todoToDelete = toDoService.readById(todoId);
        if (todoToDelete != null) {
            toDoService.delete(todoId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{todo_id}/collaborators")
    public ResponseEntity<List<UserDTO>> getCollaborators(@PathVariable("todo_id") long todoId) {
        ToDo todo = toDoService.readById(todoId);
        if (todo != null) {
            List<UserDTO> collaboratorDTOs = todo.getCollaborators().stream()
                    .map(UserDTO::mapToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(collaboratorDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{todo_id}/collaborators")
    public ResponseEntity<List<UserDTO>> addCollaborator(@PathVariable("u_id") long collabId, @PathVariable("todo_id") long todoId) {
        ToDo toChange = toDoService.readById(todoId);
        if (toChange != null) {
            User collaborator = userService.readById(collabId);
            if (collaborator != null) {
                List<User> oldCollab = toChange.getCollaborators();
                oldCollab.add(collaborator);
                toChange.setCollaborators(oldCollab);
                toDoService.update(toChange);
                List<UserDTO> updatedCollaboratorDTOs = toChange.getCollaborators().stream()
                        .map(UserDTO::mapToDTO)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(updatedCollaboratorDTOs);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{todo_id}/collaborators")
    public ResponseEntity<List<UserDTO>> deleteCollaborator(@PathVariable("u_id") long collabId, @PathVariable("todo_id") long todoId) {
        ToDo toChange = toDoService.readById(todoId);
        if (toChange != null) {
            User collaborator = userService.readById(collabId);
            if (collaborator != null) {
                List<User> oldCollab = toChange.getCollaborators();
                oldCollab.remove(collaborator);
                toChange.setCollaborators(oldCollab);
                toDoService.update(toChange);
                List<UserDTO> updatedCollaboratorDTOs = toChange.getCollaborators().stream()
                        .map(UserDTO::mapToDTO)
                        .collect(Collectors.toList());
                return ResponseEntity.ok(updatedCollaboratorDTOs);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}