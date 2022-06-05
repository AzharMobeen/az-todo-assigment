package com.az.assignment.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
public class ToDoItem implements Serializable {

    @NotEmpty(message = "Todo activity is require")
    private String activity;
}