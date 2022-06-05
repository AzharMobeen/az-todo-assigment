package com.az.assignment.dto;

import com.az.assignment.entity.ToDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultipleToDoResponse implements Serializable {

    List<ToDo> todoList;
}
