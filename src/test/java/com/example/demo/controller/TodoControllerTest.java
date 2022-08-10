package com.example.demo.controller;

import com.example.demo.dto.TodoDto;
import com.example.demo.model.TodoEntity;
import com.example.demo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    public void tearDown() throws Exception {
        todoRepository.deleteAll();
    }

    @Test
    public void postTest() throws Exception {
        //given
        String title = "테스트 포스트1";
        TodoDto todoDto = TodoDto.builder()
                .title(title)
                .build();
        String url = "http://localhost:" + port + "/todo";

        //when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, todoDto, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TodoEntity> todoEntities = todoRepository.findAll();
        assertThat(todoEntities.get(0).getTitle()).isEqualTo(title);
    }

    @Test
    public void updateTest() throws Exception {
        //given
        String title = "테스트 포스트1";
        TodoDto todoDto = TodoDto.builder()
                .title(title)
                .build();
        String url = "http://localhost:" + port + "/todo";
        restTemplate.postForEntity(url, todoDto, String.class);

        String targetPostId = todoRepository.findAll().get(0).getId();
        String expectedTitle = "테스트 포스트 수정1";
        boolean expectedDone = true;

        TodoDto updateTodoDto = TodoDto.builder()
                .id(targetPostId)
                .title(expectedTitle)
                .done(expectedDone)
                .build();

        HttpEntity<TodoDto> requestEntity = new HttpEntity<>(updateTodoDto);

        //when
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<TodoEntity> todoEntities = todoRepository.findAll();
        assertThat(todoEntities.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(todoEntities.get(0).isDone()).isEqualTo(expectedDone);
    }
}
