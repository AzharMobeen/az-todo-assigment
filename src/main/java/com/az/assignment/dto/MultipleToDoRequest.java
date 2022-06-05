package com.az.assignment.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Data
public class MultipleToDoRequest implements Serializable {

    @NotEmpty(message = "Please provide multiple ToDos")
    Set<ToDoRequest> toDoRequests;
}