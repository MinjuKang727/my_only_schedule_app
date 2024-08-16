package com.sparta.my_only_schedule_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManagerRequestDto {
    private String name;
    private String email;
    private String pw;
}
