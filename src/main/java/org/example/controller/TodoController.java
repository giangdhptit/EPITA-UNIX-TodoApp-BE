package org.example.controller;

import org.example.dto.TodoEntity;
import org.example.repository.TodoRepository;
import org.example.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoService todoService;

    @RestController
    public class HelloController {
        @GetMapping("/api/demo-web")
        public String Hello() {
            return "Manage your schedule effortlessly with Todoapp!";
        }
    }


    @GetMapping("/get-all")
    public List<TodoEntity> getAll() {
        return todoRepository.findAll();
    }

    @GetMapping("/get-by-id/{id}")
    public TodoEntity getById(@PathVariable Long id) {
        return todoRepository.findById(id).get();
    }

    //create
    @PostMapping("/save")
    public TodoEntity save(@RequestBody TodoEntity todoItem) {
        return todoRepository.save(todoItem);
    }

    @PostMapping("/update/{id}")
    public TodoEntity update(@PathVariable long id, @RequestBody TodoEntity todoItem) {
        todoItem.setId(id);
        return todoRepository.save(todoItem);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        todoRepository.deleteById(id);
    }

    @GetMapping("/mail")
    public void mail() {
         todoService.sendSimpleEmail();
    }
}

