package com.sparta.my_only_schedule_app.dto;

import com.sparta.my_only_schedule_app.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private long schedule_id;
    private String todo;
    private String name;
    private String submit_date;
    private String edit_date;
}
