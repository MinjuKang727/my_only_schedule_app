package com.sparta.my_only_schedule_app.controller;

import com.sparta.my_only_schedule_app.dto.request.ScheduleCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.request.ScheduleUpdateRequestDto;
import com.sparta.my_only_schedule_app.dto.response.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.exception.CommonException;
import com.sparta.my_only_schedule_app.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * 일정 등록
     * @param requestDto : 일정 등록 정보를 받는 객체
     * @return 등록된 일정 객체
     */
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleCreateRequestDto requestDto) throws CommonException {
        return ResponseEntity.ok(this.scheduleService.saveSchedule(requestDto));
    }


    /**
     * 일정 조회(단건 조회)
     * @param id : 조회할 일정 고유 번호
     * @return 조회된 일정 정보를 담은 객체
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable long id) throws CommonException {
        return ResponseEntity.ok(this.scheduleService.getSchedule(id));
    }

    /**
     * 일정 수정
     * @param id : 수정할 일정 고유 번호
     * @param requestDto : 수정할 일정 정보를 담은 객체
     * @return ScheduleResponseDto : 수정된 일정 정보를 담은 객체
     */
    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable long id, @RequestBody @Valid ScheduleUpdateRequestDto requestDto) throws CommonException {
        return ResponseEntity.ok(this.scheduleService.updateSchedule(id, requestDto));
    }
}
