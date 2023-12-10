package com.softserve.itacademy.todolist.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class TaskRequest {
    String name;
    String priority;
    String state;

    @JsonCreator
    public TaskRequest(@JsonProperty("name") @NotNull String name,
                       @JsonProperty("priority") @NotNull String priority,
                       @JsonProperty("state") String stateId) {
        this.state = state;
        this.name = name;
        this.priority = priority;
    }
}
