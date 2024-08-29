package com.sparta.my_only_schedule_app.dto.schedule.response;

import com.sparta.my_only_schedule_app.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleReadAllResponseDto {
    private Long scheduleId;
    private String title;
    private String toDo;
    private String createrName;
    private int commentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleReadAllResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.title = schedule.getTitle();
        this.toDo = schedule.getToDo();
        this.createrName = schedule.getCreaterName();
        this.commentCnt = schedule.getCommentList().size();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
