package com.az.assignment.repository;

import com.az.assignment.entity.ToDo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @EntityGraph(attributePaths = {"todoItems"})
    List<ToDo> findAll();

    @EntityGraph(attributePaths = {"todoItems"})
    Optional<ToDo> findByToDoId(Long toDoId);
}
