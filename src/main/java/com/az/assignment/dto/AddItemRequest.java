package com.az.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest implements Serializable {

    @NotEmpty(message = "Please provide ToDo Item activity")
    private String activity;
}
