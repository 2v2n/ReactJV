package com.example.backend.service;

import com.example.backend.domain.Category;
import com.example.backend.domain.Todo;
import com.example.backend.dto.TodoRequestDto;
import com.example.backend.dto.TodoResponseDto;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // 이 클래스가 비즈니스 로직을 담당하는 서비스 계층의 빈(Bean)임을 나타냅니다.
@RequiredArgsConstructor // final이 붙은 필드를 포함하는 생성자를 자동으로 생성합니다. (생성자 주입)
@Transactional(readOnly = true) // 클래스 내 모든 public 메서드에 읽기 전용 트랜잭션을 적용합니다.
public class TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 조건에 따라 할 일 목록을 검색하고 최신순으로 정렬하여 반환합니다.
     * @param keyword 검색할 제목 키워드 (null 가능)
     * @param completed 필터링할 완료 상태 (null 가능)
     * @return 조건에 맞는 할 일 목록
     */
    public List<TodoResponseDto> searchTodos(String keyword, Boolean completed) {
        List<Todo> todos = todoRepository.findAllByOrderByIdDesc();

        return todos.stream()
                .filter(todo -> keyword == null || keyword.trim().isEmpty() || todo.getTitle().contains(keyword))
                .filter(todo -> completed == null || todo.isCompleted() == completed)
                .map(TodoResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 새로운 할 일을 저장합니다.
     */
    @Transactional // 이 메서드는 데이터를 변경하므로, 별도의 쓰기 가능한 트랜잭션을 적용합니다.
    public TodoResponseDto save(TodoRequestDto requestDto) {
        Todo todo = requestDto.toEntity();

        // categoryId가 있으면 Category를 찾아서 설정
        if (requestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리가 없습니다. id=" + requestDto.getCategoryId()));
            todo.setCategory(category);
        }

        Todo savedTodo = todoRepository.save(todo);
        return TodoResponseDto.from(savedTodo);
    }

    /**
     * 특정 할 일을 삭제합니다.
     */
    @Transactional // 쓰기 트랜잭션 적용
    public void delete(Long id) {
        // findById로 존재 여부를 먼저 확인하고 삭제하는 것이 더 안전하지만,
        // deleteById는 ID가 없어도 오류를 발생시키지 않으므로 간단하게 구현합니다.
        todoRepository.deleteById(id);
    }

    /**
     * 특정 할 일을 수정합니다.
     * @param id 수정할 할 일의 ID
     * @param requestDto 수정할 내용을 담은 DTO
     * @return 수정된 Todo 객체
     */
    @Transactional // 쓰기 트랜잭션 적용
    public TodoResponseDto update(Long id, TodoRequestDto requestDto) {
        // 1. ID로 기존 Todo 엔티티를 조회합니다. 없으면 예외를 발생시킵니다.
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 할 일이 없습니다. id=" + id));

        // 2. DTO의 정보로 엔티티 상태를 변경합니다. (더티 체킹 활용)
        requestDto.updateEntity(todo);

        // 카테고리 변경 로직 추가
        if (requestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리가 없습니다. id=" + requestDto.getCategoryId()));
            todo.setCategory(category);
        }

        // 3. @Transactional에 의해 메서드 종료 시 변경된 내용이 자동으로 DB에 반영됩니다.
        return TodoResponseDto.from(todo);
    }
}