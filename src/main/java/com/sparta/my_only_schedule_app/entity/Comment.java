package com.sparta.my_only_schedule_app.entity;

import com.sparta.my_only_schedule_app.dto.comment.request.CommentCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", updatable = false)
    private Long commentId;

    @Column(name="creater_name", updatable = false, nullable = false, length = 20)
    private String createrName;

    @Column(name="contents", nullable = false, length = 1000)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Comment(CommentCreateRequestDto requestDto) {
        this.createrName = requestDto.getCreaterName();
        this.contents = requestDto.getContents();
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
