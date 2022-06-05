package com.az.assignment.controller;

import com.az.assignment.dto.AddItemRequest;
import com.az.assignment.dto.MultipleToDoRequest;
import com.az.assignment.dto.MultipleToDoResponse;
import com.az.assignment.dto.UpdateItemRequest;
import com.az.assignment.entity.ToDo;
import com.az.assignment.exception.ErrorMessage;
import com.az.assignment.service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/toDos")
public class ToDoController {

    private final ToDoService toDoService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MultipleToDoResponse.class))}),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @Operation(summary = "Ability to list the added to dos in the list")
    @GetMapping
    public MultipleToDoResponse getAllToDos(@RequestHeader("userId") long userId) {
        return toDoService.fetchToDos(userId);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MultipleToDoResponse.class))}),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )}),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @Operation(summary = "Ability to make multiple to do lists")
    @PostMapping
    public MultipleToDoResponse createMultipleToDoList(@RequestHeader("userId") long userId,
                                                       @Valid @RequestBody MultipleToDoRequest request) {
        return toDoService.createMultipleToDos(request, userId);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ToDo.class)
        )}),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )}),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )}),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @Operation(summary = " Ability to add a to do")
    @PostMapping("/{toDoId}/items")
    public ToDo addItemInToDoList(@RequestHeader("userId") long userId, @PathVariable Long toDoId,
                                  @Valid @RequestBody AddItemRequest request) {
        return toDoService.addNewItem(toDoId, request, userId);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ToDo.class)
        )}),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )}),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )}),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @Operation(summary = "Ability to edit the to do")
    @PutMapping("/{toDoId}/items")
    public ToDo updateItemInToDoList(@RequestHeader("userId") long userId, @PathVariable Long toDoId,
                                     @Valid @RequestBody UpdateItemRequest request) {
        return toDoService.updateItemActivity(toDoId, request, userId);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Boolean.class)
        )}),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )}),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @Operation(summary = "Ability to delete the to do")
    @DeleteMapping("/{toDoId}/items/{itemId}")
    public boolean removeItemFromToDoList(@RequestHeader("userId") long userId, @PathVariable long toDoId,
                                          @PathVariable long itemId) {
        return toDoService.deleteItem(toDoId, itemId, userId);
    }
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = {@Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ToDo.class)
        )}),
        @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )}),
        @ApiResponse(responseCode = "404", description = "NOT FOUND", content = {@Content(
                schema = @Schema(implementation = ErrorMessage.class)
        )})
    })
    @Operation(summary = "Ability to mark the to do as done")
    @PutMapping("/{toDoId}/items/{itemId}")
    public ToDo markItemActivityDoneInToDoList(@RequestHeader("userId") long userId, @PathVariable long toDoId,
                                               @PathVariable long itemId) {
        return toDoService.markDoneItemActivity(toDoId, itemId, userId);
    }
}