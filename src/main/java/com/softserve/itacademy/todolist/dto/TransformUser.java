package com.softserve.itacademy.todolist.dto;

import com.softserve.itacademy.todolist.model.Role;
import com.softserve.itacademy.todolist.model.User;

public class TransformUser {

    public static User fromRequestToUser(UserRequest userRequest, Role role) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(role);
        return user;
    }
}
