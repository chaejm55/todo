package com.example.demo.controller;

import com.example.demo.dto.TodoDto;
import com.example.demo.model.TodoEntity;
import com.example.demo.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.InstanceOfAssertFactories.collection;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
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
    public void putPostsTest() throws Exception {
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
}
