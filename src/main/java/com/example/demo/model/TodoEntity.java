package com.example.demo.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor // 필수조건 1
@AllArgsConstructor
@Getter // @Data 대신 Getter만 생성
@Entity
@Table(name = "Todo")
public class TodoEntity {
    @Id // 필수조건 2
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String userId;
    private String title;
    private boolean done;

    // setter 대신 사용
    public void makeInitialUserInfo(String id, String userId) {
        this.id = id;
        this.userId = userId;
    }


    public void overwriteInfo(TodoEntity entity) {
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    // setter 대신 사용
    public void makeTempUserId(String tempUserId) {
        this.userId = tempUserId;
    }
}
