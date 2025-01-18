package org.example.dto;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String todoItem;
    private String completed;
    public TodoEntity(String todoItem, String completed) {
        super();
        this.todoItem = todoItem;
        this.completed = completed;
    }



}
