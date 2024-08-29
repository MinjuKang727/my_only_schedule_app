package com.sparta.my_only_schedule_app.dto.comment.response;

import com.sparta.my_only_schedule_app.entity.Comment;
import com.sparta.my_only_schedule_app.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private Long scheduleId;
    private String contents;
    private String createrName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.createrName = comment.getCreaterName();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.scheduleId = comment.getSchedule().getScheduleId();
    }
}
