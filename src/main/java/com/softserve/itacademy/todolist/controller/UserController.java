package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.TransformUser;
import com.softserve.itacademy.todolist.dto.UserRequest;
import com.softserve.itacademy.todolist.dto.UserResponse;
import com.softserve.itacademy.todolist.model.User;
import com.softserve.itacademy.todolist.service.RoleService;
import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @GetMapping
    List<UserResponse> getAll() {
        return userService.getAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        User user = userService.create(TransformUser.fromRequestToUser(userRequest, roleService.readById(2)));
        return new UserResponse(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public UserResponse getUser(@PathVariable long id) {
        return new UserResponse(userService.readById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.checkUserId(#auth,#id)")
    public UserResponse updateUser(
            @PathVariable long id,
            @RequestBody UserRequest userRequest,
            Authentication auth) {
        User user = TransformUser.fromRequestToUser(userRequest, roleService.findByName(userRequest.getRole()));
        user.setId(id);
        userService.update(user);
        return new UserResponse(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @userSecurity.checkUserId(#auth,#id)")
    public ResponseEntity<Object> deleteUser(@PathVariable long id,
                                             Authentication auth) {
        userService.delete(id);
        return ResponseEntity.ok("Success delete user " + id);
    }
}
