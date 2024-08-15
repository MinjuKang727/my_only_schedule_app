package com.sparta.my_only_schedule_app.dto;

import com.sparta.my_only_schedule_app.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    private String pw;
    private String todo;
    private String name;

    ScheduleRequestDto(String pw) {
        this.pw = pw;
    }
}
