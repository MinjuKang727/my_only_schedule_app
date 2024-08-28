package com.sparta.my_only_schedule_app.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ScheduleMultiReadRequestDto {

    private final LocalDateTime createdAt;
    private final String createrName;
}
