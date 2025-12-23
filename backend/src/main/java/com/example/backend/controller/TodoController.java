package com.example.backend.controller;

import com.example.backend.dto.TodoRequestDto;
import com.example.backend.dto.TodoResponseDto;
import com.example.backend.service.TodoService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 이 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타냅니다.
@RequestMapping("/api/todos") // 이 컨트롤러의 모든 메서드에 대한 기본 URL 경로를 '/api/todos'로 설정합니다.
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 생성합니다. (생성자 주입)
@CrossOrigin(origins = "http://localhost:5173") // React 개발 서버(Vite)からのリクエストを許可します。
public class TodoController {

    private final TodoService todoService;

    /**
     * GET /api/todos
     * 조건에 따라 할 일 목록을 조회합니다.
     * 예: /api/todos?completed=true
     * 예: /api/todos?keyword=공부
     * 예: /api/todos?completed=false&keyword=장보기
     */
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> searchTodos(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean completed) {
        List<TodoResponseDto> todos = todoService.searchTodos(keyword, completed);
        return ResponseEntity.ok(todos);
    }

    /**
     * POST /api/todos
     * 새로운 할 일을 생성합니다.
     */
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto createdTodo = todoService.save(requestDto);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    /**
     * PUT /api/todos/{id}
     * 특정 할 일을 수정합니다.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto updatedTodo = todoService.update(id, requestDto);
        return ResponseEntity.ok(updatedTodo);
    }

    /**
     * DELETE /api/todos/{id}
     * 특정 할 일을 삭제합니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content 응답을 반환합니다.
    }
}