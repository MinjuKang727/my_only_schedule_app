package com.sparta.my_only_schedule_app.dto.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleCreateRequestDto {
    @NotBlank(message = "작성 유저명은 null 혹은 공백일 수 없습니다.")
    @Size(min = 1, max = 20, message = "작성 유저명은 20자 이하이어야 합니다.")
    private String createrName;

    @NotBlank(message = "일정 제목은 null 혹은 공백일 수 없습니다.")
    @Size(min = 1, max = 100, message = "일정 제목은 100자 이하이어야 합니다.")
    private String title;

    @NotBlank(message = "일정 내용은 null 혹은 공백일 수 없습니다.")
    @Size(min = 1, max = 200, message = "일정 내용은 200자 이하이어야 합니다.")
    private String toDo;

    public ScheduleCreateRequestDto(String createrName, String title, String toDo) {
        this.createrName = createrName;
        this.title = title;
        this.toDo = toDo;
    }
}
