package com.sparta.my_only_schedule_app.dto.schedule.response;

import com.sparta.my_only_schedule_app.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long scheduleId;
    private String title;
    private String toDo;
    private String createrName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.toDo = schedule.getToDo();
        this.createrName = schedule.getCreaterName();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
