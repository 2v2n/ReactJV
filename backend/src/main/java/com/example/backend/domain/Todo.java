package com.example.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 이 클래스가 데이터베이스 테이블과 매핑되는 엔티티임을 나타냅니다.
@Getter // Lombok: 모든 필드에 대한 Getter 메서드를 자동 생성합니다.
@Setter // Lombok: 모든 필드에 대한 Setter 메서드를 자동 생성합니다.
@NoArgsConstructor // Lombok: 파라미터가 없는 기본 생성자를 자동 생성합니다.
@AllArgsConstructor // Lombok: 모든 필드를 파라미터로 받는 생성자를 자동 생성합니다.
public class Todo {

    @Id // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임합니다 (AUTO_INCREMENT).
    private Long id;

    private String title;

    private boolean completed;

    private String content;

    @ManyToOne
    private Category category;
}
