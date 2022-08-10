package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestRequestBodyDto {
    private int id;
    private String message;
}
