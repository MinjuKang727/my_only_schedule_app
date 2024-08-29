package com.sparta.my_only_schedule_app.entity;

import com.sparta.my_only_schedule_app.dto.schedule.request.ScheduleCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.schedule.request.ScheduleUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id", updatable = false)
    private Long scheduleId;

    @Column(name="creater_name", updatable = false, nullable = false, length = 20)
    private String createrName;

    @Column(name="title", nullable = false, length = 100)
    private String title;

    @Column(name="to_do", nullable = false, length = 200)
    private String toDo;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    public Schedule(ScheduleCreateRequestDto requestDto) {
        this.createrName = requestDto.getCreaterName();
        this.title = requestDto.getTitle();
        this.toDo = requestDto.getToDo();
    }

    public void update(ScheduleUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.toDo = requestDto.getToDo();
    }
}
