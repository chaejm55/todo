package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id; // 사용자 고유 id
    
    @Column(nullable = false)
    private String userName; // 사용자 이름
    
    @Column(nullable = false)
    private String email; // 사용자의 email, 아이디와 같은 기능
    
    @Column(nullable = false)
    private String password; // 비밀번호
}
