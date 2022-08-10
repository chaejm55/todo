package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {
        TodoEntity entity = TodoEntity.builder()
                .title("My first todo item")
                .build();
        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);
        // 엔티티 id를 통해 존재하는 TodoEntity를 가져옴
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            // 새 엔티티 값으로 덮어씌워줌
            todo.overwriteInfo(entity);
            repository.save(todo);
        });
        
        // 해당 유저의 모든 Todo리스트 리턴
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity ", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
