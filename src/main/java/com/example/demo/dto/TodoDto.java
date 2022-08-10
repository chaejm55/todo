package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoDto {
    private String id;
    private String title;
    private boolean done;
    // 보안상 userId는 숨김, 시큐리티를 통해 구현

    public TodoDto(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDto dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
