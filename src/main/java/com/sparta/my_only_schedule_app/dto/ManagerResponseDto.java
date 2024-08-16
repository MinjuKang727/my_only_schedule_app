package com.sparta.my_only_schedule_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManagerResponseDto {
    private long manager_id;
    private String name;
    private String email;
    private String submit_date;
    private String edit_date;
}
