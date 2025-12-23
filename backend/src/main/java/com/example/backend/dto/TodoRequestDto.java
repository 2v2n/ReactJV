package com.example.backend.dto;

import com.example.backend.domain.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoRequestDto {
    private String title;
    private Boolean completed;
    private String content;
    private Long categoryId;

    public Todo toEntity() {
        // 서비스 계층에서 Category를 설정할 것이므로, 여기서는 Category 필드를 null로 둡니다.
        return new Todo(null, this.title, this.completed != null ? this.completed : false, this.content, null);
    }

    public void updateEntity(Todo todo) {
        if (this.title != null) {
            todo.setTitle(this.title);
        }
        if (this.content != null) {
            todo.setContent(this.content);
        }
        if (this.completed != null) {
            todo.setCompleted(this.completed);
        }
    }
}
