package com.sparta.my_only_schedule_app.entity;

import com.sparta.my_only_schedule_app.dto.ManagerRequestDto;
import com.sparta.my_only_schedule_app.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Manager {
    private long manager_id;
    private String name;
    private String email;
    private String submit_date;
    private String edit_date;
    private String pw;

    public Manager(ManagerRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.pw = requestDto.getPw();
    }
}
