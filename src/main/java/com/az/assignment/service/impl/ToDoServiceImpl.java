package com.az.assignment.service.impl;

import com.az.assignment.dto.*;
import com.az.assignment.entity.Item;
import com.az.assignment.entity.ToDo;
import com.az.assignment.exception.CustomRuntimeException;
import com.az.assignment.repository.ToDoRepository;
import com.az.assignment.service.ToDoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repository;

    @Transactional
    @Override
    public MultipleToDoResponse createMultipleToDos(MultipleToDoRequest request, Long userId) {
        log.info("createMultipleToDos request {}, userId {}", request, userId);
        MultipleToDoResponse response = null;
        if(!CollectionUtils.isEmpty(request.getToDoRequests())) {
            List<ToDo> toDos = request.getToDoRequests().stream().map(this::buildToDoObject)
                    .map(toDo ->  linkUser(toDo, userId))
                    .collect(Collectors.toList());
            toDos = repository.saveAll(toDos);
            response = new MultipleToDoResponse(toDos);
        }
        log.info("createMultipleToDos response {}", response);
        return response;
    }

    @Transactional(readOnly = true)
    @Override
    public MultipleToDoResponse fetchToDos(long userId) {
        log.info("fetchToDos userId {}::", userId);
        List<ToDo> toDos = repository.findAll();
        MultipleToDoResponse response = new MultipleToDoResponse(toDos);
        log.info("fetchToDos response {}", response);
        return response;
    }

    @Transactional
    @Override
    public ToDo addNewItem(Long toDoId, AddItemRequest request, Long userId) {
        log.info("addNewItem toDoId {}, request {}, userId {}", toDoId, request, userId);
        ToDo toDo = fetchValidatedToDo(toDoId, userId);
        Item newItem = new Item();
        newItem.setActivity(request.getActivity());
        newItem.setStatus(ActivityStatus.PENDING);
        addToDoItem(newItem, toDo);
        toDo = repository.save(toDo);
        log.info("addNewItem response {}", toDo);
        return toDo;
    }

    @Transactional
    @Override
    public ToDo updateItemActivity(Long toDoId, UpdateItemRequest request, Long userId) {
        log.info("updateItemActivity toDoId {}, request {}, userId {}", toDoId, request, userId);
        ToDo toDo = fetchValidatedToDo(toDoId, userId);
        if(!CollectionUtils.isEmpty(toDo.getTodoItems())) {
            Item requestedItem = validateAndFetchToDoItem(request.getItemId(), toDo);
            requestedItem.setActivity(request.getActivity());
            toDo = repository.save(toDo);
            log.info("updateItemActivity response {}", toDo);
            return toDo;
        }
        throw new CustomRuntimeException("ToDo item not exist", "Provided itemId not exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public boolean deleteItem(long toDoId, long itemId, long userId) {
        log.info("deleteItem toDoId {}, itemId {}, userId {}", toDoId, itemId, userId);
        ToDo toDo = validateAndFetchRequestedToDo(toDoId);
        if(!CollectionUtils.isEmpty(toDo.getTodoItems())) {
            Item requestedItem = validateAndFetchToDoItem(itemId, toDo);
            toDo.getTodoItems().remove(requestedItem);
            repository.save(toDo);
            return true;
        }
        throw new CustomRuntimeException("ToDo item not exist", "Provided itemId not exist", HttpStatus.NOT_FOUND);
    }

    @Override
    public ToDo markDoneItemActivity(long toDoId, long itemId, long userId) {
        log.info("markDoneItemActivity toDoId {}, itemId {}, userId {}", toDoId, itemId, userId);
        ToDo toDo = fetchValidatedToDo(toDoId, userId);
        if(!CollectionUtils.isEmpty(toDo.getTodoItems())) {
            Item requestedItem = validateAndFetchToDoItem(itemId, toDo);
            requestedItem.setStatus(ActivityStatus.DONE);
            toDo = repository.save(toDo);
            log.info("markDoneItemActivity response {}", toDo);
            return toDo;
        }
        throw new CustomRuntimeException("ToDo item not exist", "Provided itemId not exist", HttpStatus.NOT_FOUND);
    }

    private ToDo fetchValidatedToDo(long toDoId, long userId) {
        ToDo toDo = validateAndFetchRequestedToDo(toDoId);
        validateToDoOwner(toDo, userId);
        return toDo;
    }

    private void validateToDoOwner(ToDo toDo, Long userId) {
        if(!toDo.getCreatedBy().equals(userId)) {
            throw new CustomRuntimeException("Not Authorized", "Provided user is not own this ToDo list",
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private ToDo buildToDoObject(ToDoRequest request) {
        ToDo toDo = new ToDo();
        toDo.setType(request.getType());
        request.getTodoList().stream().map(this ::buildToDoItem)
                .forEach(item -> addToDoItem(item, toDo));
        return toDo;
    }

    private Item buildToDoItem(ToDoItem toDoItem) {
        Item item = new Item();
        item.setActivity(toDoItem.getActivity());
        item.setStatus(ActivityStatus.PENDING);
        return item;
    }

    private void addToDoItem(Item item, ToDo toDo) {
        if(toDo.getTodoItems() == null)
            toDo.setTodoItems( new HashSet<>());
        item.setToDo(toDo);
        toDo.getTodoItems().add(item);
    }

    private ToDo linkUser(ToDo toDo, Long userId) {
        toDo.setCreatedBy(userId);
        return toDo;
    }

    private ToDo validateAndFetchRequestedToDo(Long toDoId) {
        Optional<ToDo> requestedToDo = repository.findByToDoId(toDoId);
        return requestedToDo.orElseThrow(() -> new CustomRuntimeException("ToDo not exist",
                "Provided ToDoId not exist", HttpStatus.NOT_FOUND));
    }

    private Item validateAndFetchToDoItem(long itemId, ToDo toDo) {
        return toDo.getTodoItems().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst().orElseThrow(() -> new CustomRuntimeException("ToDo item not exist",
                        "Provided itemId not exist", HttpStatus.NOT_FOUND));
    }
}