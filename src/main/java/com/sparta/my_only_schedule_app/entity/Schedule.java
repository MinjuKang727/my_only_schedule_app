package com.sparta.my_only_schedule_app.entity;

import com.sparta.my_only_schedule_app.dto.request.ScheduleCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.request.ScheduleUpdateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name="creater_name", updatable = false, nullable = false, length = 20)
    private String createrName;

    @Column(name="title", nullable = false, length = 100)
    private String title;

    @Column(name="to_do", nullable = false, length = 200)
    private String toDo;

    public void update(ScheduleUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.toDo = requestDto.getToDo();
    }

    public Schedule(ScheduleCreateRequestDto requestDto) {
        this.createrName = requestDto.getCreaterName();
        this.title = requestDto.getTitle();
        this.toDo = requestDto.getToDo();
    }
}
