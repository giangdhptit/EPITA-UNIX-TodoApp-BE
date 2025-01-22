package org.example.controller;

import org.example.dto.TodoEntity;
import org.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/get-all")
    public List<TodoEntity> getAll() {
        return todoRepository.findAll();
    }

    @GetMapping("/get-by-id")
    public TodoEntity getById(@PathVariable Long id) {
        return todoRepository.findById(id).get();
    }

    @PostMapping("/save")
    public TodoEntity save(@RequestBody TodoEntity todoItem) {
        return todoRepository.save(todoItem);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        todoRepository.deleteById(id);
    }//
}
