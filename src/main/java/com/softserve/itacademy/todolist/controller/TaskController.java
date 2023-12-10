package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.TaskRequest;
import com.softserve.itacademy.todolist.dto.TaskResponse;
import com.softserve.itacademy.todolist.dto.TransformTask;
import com.softserve.itacademy.todolist.model.Task;
import com.softserve.itacademy.todolist.service.StateService;
import com.softserve.itacademy.todolist.service.TaskService;
import com.softserve.itacademy.todolist.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/users/{user_id}/todos/{todo_id}/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ToDoService toDoService;
    private final StateService stateService;

    @GetMapping
    @ResponseStatus(OK)
    public List<TaskResponse> getAll(@PathVariable("todo_id") long todoId) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        List<Task> tasks = toDoService.readById((todoId)).getTasks();
        for (Task temp : tasks) {
            taskResponses.add(new TaskResponse(temp));
        }
        return taskResponses;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public TaskResponse create(@Validated @RequestBody TaskRequest task, @PathVariable("todo_id") long todoId) {
        return new TaskResponse(taskService.create(TransformTask.transformFromRequestToModel(task, toDoService.readById(todoId), stateService.getByName("NEW"))));
    }

    @PutMapping("/{task_id}")
    @ResponseStatus(OK)
    public TaskResponse update(@Validated @RequestBody TaskRequest task, @PathVariable("task_id") long taskId, @PathVariable("todo_id") long todoId) {
        Task taskToUpdate = TransformTask.transformFromRequestToModel(task, toDoService.readById(todoId), stateService.getByName(task.getState()));
        taskToUpdate.setId(taskId);
        return new TaskResponse(taskService.update(taskToUpdate));
    }

    @DeleteMapping("/{task_id}")
    @ResponseStatus(OK)
    public ResponseEntity<Object> delete(@PathVariable("task_id") long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.ok("Success delete task " + taskId);
    }

    @GetMapping("/{task_id}")
    @ResponseStatus(OK)
    public TaskResponse getTask(@PathVariable("task_id") long taskId) {
        return new TaskResponse(taskService.readById(taskId));
    }
}


