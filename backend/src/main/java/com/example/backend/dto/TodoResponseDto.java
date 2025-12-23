package com.example.backend.dto;

import com.example.backend.domain.Todo;
// Category 엔티티는 Todo 엔티티를 통해 사용되므로 import 해주는 것이 좋습니다.
// 해당 엔티티가 Todo와 같은 패키지에 있다면 import com.example.backend.domain.Category;
// 다른 패키지에 있다면 해당 경로를 사용해야 합니다. 
// 일단 Todo 엔티티와 동일한 패키지에 있다고 가정하고 명시적인 import를 생략하겠습니다. 
// 만약 컴파일 오류가 난다면, Category 엔티티를 import 해줘야 합니다.
import lombok.Getter;

@Getter
public class TodoResponseDto {
    private final Long id;
    private final String title;
    private final boolean completed;
    private final String content;
    private final CategoryInfo category; // 내부 DTO 타입

    /**
     * Category 엔티티 정보를 담는 내부 DTO
     */
    @Getter
    public static class CategoryInfo {
        private final Long id;
        private final String name;

        // 1. DTO 필드를 직접 받아서 생성하는 생성자 (선택 사항)
        public CategoryInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        // 2. ✅ (수정된 부분) Category 엔티티를 받아서 DTO를 생성하는 생성자
        // 이 생성자를 통해 오류를 해결했습니다.
        public CategoryInfo(com.example.backend.domain.Category categoryEntity) {
            // Category 엔티티에 getId()와 getName() 메서드가 있다고 가정
            this.id = categoryEntity.getId();
            this.name = categoryEntity.getName();
        }
    }

    // private 생성자: Todo 엔티티를 받아서 DTO 객체를 초기화
    private TodoResponseDto(Todo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.completed = entity.isCompleted();
        this.content = entity.getContent();
        
        // Category 엔티티가 null인지 확인 후, 수정된 CategoryInfo 생성자를 호출
        this.category = entity.getCategory() != null 
                        ? new CategoryInfo(entity.getCategory()) 
                        : null;
    }

    // 정적 팩토리 메서드: DTO 객체 생성을 위한 표준화된 방법 제공
    public static TodoResponseDto from(Todo entity) {
        return new TodoResponseDto(entity);
    }
}