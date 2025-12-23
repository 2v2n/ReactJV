package com.example.backend.repository;

import com.example.backend.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 이 인터페이스가 Spring의 데이터 접근 계층 빈(Bean)임을 나타냅니다.
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // JpaRepository를 상속받는 것만으로도 기본적인 CRUD 메서드(save, findById, findAll, delete 등)가 자동으로 제공됩니다.

    /**
     * Spring Data JPA의 쿼리 메서드 기능입니다.
     * 메서드 이름을 규칙에 맞게 작성하면, Spring Data JPA가 자동으로 쿼리를 생성하여 실행합니다.
     * 이 메서드는 모든 Todo 항목을 ID의 내림차순(최신순)으로 정렬하여 반환합니다.
     * SQL: SELECT * FROM todo ORDER BY id DESC;
     */
    List<Todo> findAllByOrderByIdDesc();

    // 제목에 특정 키워드가 포함된 할 일을 최신순으로 검색
    List<Todo> findByTitleContainingOrderByIdDesc(String keyword);

    // 완료 상태에 따라 할 일을 최신순으로 필터링
    List<Todo> findByCompletedOrderByIdDesc(boolean completed);

    // 제목 키워드와 완료 상태를 모두 만족하는 할 일을 최신순으로 검색
    List<Todo> findByTitleContainingAndCompletedOrderByIdDesc(String keyword, boolean completed);
}