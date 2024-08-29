package com.sparta.my_only_schedule_app.service;

import com.sparta.my_only_schedule_app.dto.schedule.request.ScheduleCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.schedule.request.ScheduleUpdateRequestDto;
import com.sparta.my_only_schedule_app.dto.schedule.response.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.entity.Schedule;
import com.sparta.my_only_schedule_app.exception.CommonException;
import com.sparta.my_only_schedule_app.exception.ExceptionCode;
import com.sparta.my_only_schedule_app.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final EntityManager em;
    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 등록
     * @param requestDto : 등록할 일정 정보를 받은 객체
     * @return 등록한 Schedule 객체
     */
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleCreateRequestDto requestDto) {
        log.trace("ScheduleService - saveSchedule() 메서드 실행");
        Schedule schedule = new Schedule(requestDto);
        this.scheduleRepository.saveAndFlush(schedule);

        Schedule savedSchedule = em.find(Schedule.class, schedule.getScheduleId());

        return new ScheduleResponseDto(savedSchedule);
    }


    /**
     * 일정 조회 by id (단건 조회)
     * @param scheduleId : 조회할 일정 고유 번호
     * @return 조회된 일정 객체
     * @throws IllegalArgumentException : 유효하지 않은 일정 고유 번호일 때, 발생
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(long scheduleId) throws CommonException {
        log.trace("ScheduleService - getSchedule() 메서드 실행");
        Schedule schedule = this.scheduleRepository.findById(scheduleId)
                                    .orElseThrow(() ->
                                        new CommonException(ExceptionCode.NO_RESULT)
                                    );

        return new ScheduleResponseDto(schedule);
    }

    /**
     * 일정 수정
     * @param requestDto : 수정할 일정 정보가 담긴 객체
     * @return 수정한 일정 정보가 담긴 객체
     * @throws IllegalArgumentException : 일정 고유 번호가 유효하지 않을 때, 발생
     */
    @Transactional
    public ScheduleResponseDto updateSchedule(long scheduleId, ScheduleUpdateRequestDto requestDto) throws CommonException {
        log.trace("ScheduleService - updateSchedule() 메서드 실행");
        Schedule schedule = this.scheduleRepository.findById(scheduleId)
                                                .orElseThrow(() ->
                                                        new CommonException(ExceptionCode.INVALID_SC_ID)
                                                );

        if (!schedule.getCreaterName().equals(requestDto.getCreaterName())) {
            throw new CommonException(ExceptionCode.INVALID_SC_CREATER);
        }

        schedule.update(requestDto);
        em.flush();

        Schedule updatedSchedule = em.find(Schedule.class, schedule.getScheduleId());

        return new ScheduleResponseDto(updatedSchedule);
    }
}
