package com.softserve.itacademy.todolist.dto;

import com.softserve.itacademy.todolist.model.Priority;
import com.softserve.itacademy.todolist.model.State;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.model.ToDo;

public class TransformTask {

    public static Task transformFromRequestToModel(TaskRequest taskRequest, ToDo todo, State state) {
        Task task = new Task();
        task.setTodo(todo);
        task.setState(state);
        task.setPriority(Priority.valueOf(taskRequest.priority));
        task.setName(taskRequest.name);
        return task;
    }
}
