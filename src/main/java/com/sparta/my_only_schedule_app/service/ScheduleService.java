package com.sparta.my_only_schedule_app.service;

import com.sparta.my_only_schedule_app.dto.ScheduleRequestDto;
import com.sparta.my_only_schedule_app.dto.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.entity.Schedule;
import com.sparta.my_only_schedule_app.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 등록
    public ScheduleResponseDto save(ScheduleRequestDto resquestDto) {
        Schedule schedule = new Schedule(resquestDto);
        Schedule saveSchedule = this.scheduleRepository.save(schedule);

        if (saveSchedule != null) {
            return this.scheduleRepository.getScheduleResponseById(saveSchedule.getId());
        }

        return null;
    }

    // 일정 조회 by id (단건 조회)
    public ScheduleResponseDto getSchedule(long id) {
        boolean ok = false;

        try {
            ok = this.scheduleRepository.checkId(id);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (ok) {
            return this.scheduleRepository.getScheduleResponseById(id);
        }

        return null;
    }

    // 일정 목록 조회 by 수정일, 담당자명 (다건 조회)
    public List<ScheduleResponseDto> getScheduleList(String edit_date, String name) {
        return this.scheduleRepository.getScheduleList(edit_date, name);
    }

    // 일정 수정
    public ScheduleResponseDto update(long id, ScheduleRequestDto requestDto) {
        boolean ok = this.scheduleRepository.checkIdPw(id, requestDto.getPw());

        if (ok) {
            this.scheduleRepository.update(id, requestDto);

            return this.scheduleRepository.getScheduleResponseById(id);
        }

        return null;
    }

    // 일정 삭제
    public long delete(long id, String pw) {
        Boolean ok = false;

        try {
            ok = this.scheduleRepository.checkIdPw(id, pw);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (ok) {
            this.scheduleRepository.delete(id);

            return id;
        } else {
           return -1;
        }
    }
}
