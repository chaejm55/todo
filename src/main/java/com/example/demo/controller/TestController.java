package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.TestRequestBodyDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController() {
        return "Hello world!";
    }

    @GetMapping("/testGetMapping")
    public String testControllerWithPath() {
        return "Hello world testGetMapping";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariable(@PathVariable(required = false) int id) {
        return "Hello world! ID " + id;
    }

    @GetMapping("/testRequestParam")
    public String testControllerWithRequestParam(@RequestParam(required = false) int id) {
        return "Hello world! ID " + id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerWithRequestBody(@RequestBody TestRequestBodyDto testRequestBodyDto) {
        return "Hello world! ID " + testRequestBodyDto.getId() + " Message: " + testRequestBodyDto.getMessage();
    }

    @GetMapping("testResponseBody")
    public ResponseDto<String> testControllerWithResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm responseDto");
        ResponseDto<String> response = ResponseDto.<String>builder().
                data(list).
                build();
        return response;
    }

    @GetMapping("testResponseEntity")
    public ResponseEntity<?> testControllerWithResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm ResponseEntity. And you got 400!");
        ResponseDto<String> response = ResponseDto.<String>builder().
                data(list).
                build();
        return ResponseEntity.badRequest().body(response);
    }
}
