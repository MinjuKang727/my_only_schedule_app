package com.sparta.my_only_schedule_app.dto.schedule.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleMultiReadRequestDto {
    private String createrName;

    public ScheduleMultiReadRequestDto(String createrName) {
        this.createrName = createrName;
    }
}
