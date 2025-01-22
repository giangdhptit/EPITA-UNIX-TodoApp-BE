package org.example.repository;


import org.example.dto.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    @Query(value = "SELECT * FROM todo t " +
            "WHERE DATE_ADD(t.deadline, INTERVAL :duration SECOND) > CURRENT_TIMESTAMP",
            nativeQuery = true)
    List<TodoEntity> getListToNotify(@Param("duration") Long duration);

}
