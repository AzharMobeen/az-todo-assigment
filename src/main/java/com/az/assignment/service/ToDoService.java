package com.az.assignment.service;

import com.az.assignment.dto.AddItemRequest;
import com.az.assignment.dto.MultipleToDoRequest;
import com.az.assignment.dto.MultipleToDoResponse;
import com.az.assignment.dto.UpdateItemRequest;
import com.az.assignment.entity.ToDo;


public interface ToDoService {
    MultipleToDoResponse createMultipleToDos(MultipleToDoRequest request, Long userId);

    MultipleToDoResponse fetchToDos(long userId);

    ToDo addNewItem(Long toDoId, AddItemRequest request, Long userId);

    ToDo updateItemActivity(Long toDoId, UpdateItemRequest request, Long userId);

    boolean deleteItem(long toDoId, long itemId, long userId);

    ToDo markDoneItemActivity(long toDoId, long itemId, long userId);
}
