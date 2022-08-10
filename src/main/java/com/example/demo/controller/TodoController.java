package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.TodoDto;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private TodoService service;
    
    private String temporaryUserId = "temporary-user"; // 임시 유저 id, 아직 시큐리티 미구현

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDto<String> response = ResponseDto.<String>builder()
                .data(list)
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto dto) {
        try {
            TodoEntity entity = TodoDto.toEntity(dto);
            entity.makeInitialUserInfo(null, temporaryUserId);
            List<TodoEntity> entities = service.create(entity);

            return ResponseEntity.ok().body(entitiesToResponse(entities));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse(e));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        List<TodoEntity> entities = service.retrieve(temporaryUserId);

        return ResponseEntity.ok().body(entitiesToResponse(entities));
    }

    @PutMapping
    public ResponseEntity<?> updateTodoList(@RequestBody TodoDto dto) {
        TodoEntity entity = TodoDto.toEntity(dto);
        entity.makeTempUserId(temporaryUserId);
        List<TodoEntity> entities = service.update(entity);

        return ResponseEntity.ok().body(entitiesToResponse(entities));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto dto) {
        try {
            TodoEntity entity = TodoDto.toEntity(dto);
            entity.makeTempUserId(temporaryUserId);
            List<TodoEntity> entities = service.delete(entity);

            return ResponseEntity.ok().body(entitiesToResponse(entities));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(errorResponse(e));
        }
    }

    private ResponseDto<TodoDto> entitiesToResponse (List<TodoEntity> entities) {
        // 자바 스트림으로 엔티티를 dto 리스트로 변환
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        // response dto를 TodoDto 리스트로 초기화
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                .data(dtos)
                .build();
        return response;
    }

    private ResponseDto<TodoDto> errorResponse (Exception e) {
        String error = e.getMessage();
        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder()
                .error(error)
                .build();
        return response;
    }
}
