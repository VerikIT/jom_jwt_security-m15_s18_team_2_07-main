package com.softserve.itacademy.todolist.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.softserve.itacademy.todolist.model.Task;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TaskResponse {
    Long id;
    String name;
    String priority;
    String state;
    String toDoName;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.priority = task.getPriority().toString();
        this.state = task.getState().getName();
        this.toDoName = task.getTodo().getTitle();
    }
}
