package com.az.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemRequest implements Serializable {

    @NotNull(message = "Please provide ItemId")
    private Long itemId;
    @NotEmpty(message = "Please provide ToDo Item activity")
    private String activity;
}
