package com.sparta.my_only_schedule_app.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentMultiReadRequestDto {
    @NotBlank(message = "작성 유저명은 null 혹은 공백일 수 없습니다.")
    @Size(min = 1, max = 20, message = "작성 유저명은 20자 이하이어야 합니다.")
    private String createrName;

    public CommentMultiReadRequestDto(String createrName) {
        this.createrName = createrName;
    }
}
