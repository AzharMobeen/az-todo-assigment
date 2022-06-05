package com.az.assignment.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Data
public class ToDoRequest implements Serializable {

    @EqualsAndHashCode.Exclude
    @NotEmpty(message = "Please provide list of todo items")
    private Set<ToDoItem> todoList;
    @NotEmpty(message = "Please provide ToDo type")
    private String type;
}
