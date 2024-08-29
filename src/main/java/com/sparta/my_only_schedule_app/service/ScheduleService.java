package com.sparta.my_only_schedule_app.service;

import com.sparta.my_only_schedule_app.dto.PagingRequestDto;
import com.sparta.my_only_schedule_app.dto.schedule.request.ScheduleCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.schedule.request.ScheduleDeleteRequestDto;
import com.sparta.my_only_schedule_app.dto.schedule.request.ScheduleUpdateRequestDto;
import com.sparta.my_only_schedule_app.dto.schedule.response.ScheduleReadAllResponseDto;
import com.sparta.my_only_schedule_app.dto.schedule.response.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.entity.Schedule;
import com.sparta.my_only_schedule_app.exception.CommonException;
import com.sparta.my_only_schedule_app.exception.ExceptionCode;
import com.sparta.my_only_schedule_app.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
     * 일정 전체 조회(다건 조회)
     * @param requestDto : 페이징 조건 데이터를 담은 객체
     * @return : 페이징된 일정 조회 결과를 담은 객체
     * @throws CommonException : 조회 결과가 존재하지 않을 때, 발생
     */
    @Transactional(readOnly = true)
    public Page<ScheduleReadAllResponseDto> getSchedules(PagingRequestDto requestDto) throws CommonException {
        log.trace("ScheduleService - getSchedules() 메서드 실행");
        Page<Schedule> scheduleList = this.scheduleRepository.findAll(requestDto.getPageable());

        if (scheduleList.isEmpty()) {
            throw new CommonException(ExceptionCode.NO_RESULT);
        }

        return scheduleList.map(ScheduleReadAllResponseDto::new);
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

    /**
     * 일정 삭제
     * @param scheduleId : 삭제할 일정 고유 번호
     * @param requestDto : 삭제할 일정 유저명을 담은 객체
     * @return 삭제된 일정 고유 번호
     * @throws CommonException : scheduleId의 일정이 존재하지 않거나 requestDto의 유저명이 작성 유저명과 일치하지 않는 경우 발생
     */
    @Transactional
    public Long deleteSchedule(Long scheduleId, ScheduleDeleteRequestDto requestDto) throws CommonException {
        log.trace("CommentService - deleteSchedule() 메서드 실행");
        Schedule schedule = this.scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new CommonException(ExceptionCode.INVALID_SC_ID)
                );

        if (!schedule.getCreaterName().equals(requestDto.getCreaterName())) {
            throw new CommonException(ExceptionCode.INVALID_SC_CREATER);
        }

        em.remove(schedule);

        return scheduleId;
    }
}
