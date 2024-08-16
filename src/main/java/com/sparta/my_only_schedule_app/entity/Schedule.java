package com.sparta.my_only_schedule_app.entity;

import com.sparta.my_only_schedule_app.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {
    private long schedule_id;
    private String pw;
    private String todo;
    private long manager_id;
    private String name;
    private String submit_date;
    private String edit_date;

    public Schedule(ScheduleRequestDto requestDto) {
        this.todo = requestDto.getTodo();
        this.manager_id = requestDto.getManager_id();
        this.pw = requestDto.getPw();
    }
}
