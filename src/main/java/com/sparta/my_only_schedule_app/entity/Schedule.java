package com.sparta.my_only_schedule_app.entity;

import com.sparta.my_only_schedule_app.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private long id;
    private String pw;
    private String todo;
    private String name;
    private String submit_date;
    private String edit_date;

    public Schedule(ScheduleRequestDto requestDto) {
        this.todo = requestDto.getTodo();
        this.name = requestDto.getName();
        this.pw = requestDto.getPw();
    }
}
