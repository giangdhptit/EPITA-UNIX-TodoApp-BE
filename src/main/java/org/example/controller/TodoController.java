package org.example.controller;

import org.example.dto.TodoEntity;
import org.example.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping
    public String index() {
        return "index.html";
    }

    @GetMapping("/todos")
    public String todos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "todos";
    }

    @PostMapping("/todoNew")
    public String add(@RequestParam String todoItem, @RequestParam
    String status, Model model) {
        TodoEntity todoEntity = new TodoEntity(todoItem, status);
        todoEntity.setTodoItem(todoItem);
        todoEntity.setCompleted(status);
        todoRepository.save(todoEntity);
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/todos";
    }

    @PostMapping("/todoDelete/{id}")
    public String delete(@PathVariable long id, Model model) {
        todoRepository.deleteById(id);
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/todos";
    }

    @PostMapping("/todoUpdate/{id}")
    public String update(@PathVariable long id, Model model) {
        Optional<TodoEntity> todoOptional = todoRepository.findById(id);
                TodoEntity todoEntity = todoOptional.get();
        if("Yes".equals(todoEntity.getCompleted())) {
            todoEntity.setCompleted("No");
        }
        else {
            todoEntity.setCompleted("Yes");
        }
        todoRepository.save(todoEntity);
        model.addAttribute("todos", todoRepository.findAll());
        return "redirect:/todos";
    }
}
