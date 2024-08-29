package com.sparta.my_only_schedule_app.dto.comment.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {
    @NotBlank(message = "작성 유저명은 null 혹은 공백일 수 없습니다.")
    @Size(min = 1, max = 20, message = "작성 유저명은 20자 이하이어야 합니다.")
    private String createrName;

    @NotBlank(message = "댓글 내용은 null 혹은 공백일 수 없습니다.")
    @Size(min = 1, max = 1000, message = "일정 제목은 1000자 이하이어야 합니다.")
    private String contents;

    CommentCreateRequestDto(String createrName, String contents) {
        this.createrName = createrName;
        this.contents = contents;
    }

}
