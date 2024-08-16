package com.sparta.my_only_schedule_app.controller;

import com.sparta.my_only_schedule_app.dto.ScheduleRequestDto;
import com.sparta.my_only_schedule_app.dto.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 생성자
    @Autowired
    ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 등록
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return this.scheduleService.save(requestDto);
    }

    // 일정 조회
    @GetMapping("/schedules/{schedule_id}")
    public ScheduleResponseDto getSchedule(@PathVariable long schedule_id) {
        return this.scheduleService.getSchedule(schedule_id);
    }

    // 일정 목록 조회
    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedules(@RequestParam(required = false) String edit_date, @RequestParam(required = false) String name) {
        return this.scheduleService.getSchedules(edit_date, name);
    }

    // 일정 수정
    @PutMapping("/schedules/{schedule_id}")
    public ScheduleResponseDto editSchedule(@PathVariable long schedule_id, @RequestBody ScheduleRequestDto requestDto) {
        return this.scheduleService.update(schedule_id, requestDto);
    }

    // 일정 삭제
    @DeleteMapping("/schedules/{schedule_id}")
    public long deleteSchedule(@PathVariable long schedule_id, @RequestBody ScheduleRequestDto requestDto) {
        return this.scheduleService.delete(schedule_id, requestDto.getPw());
    }
}
