package com.sparta.my_only_schedule_app.service;

import com.sparta.my_only_schedule_app.dto.request.ScheduleCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.request.ScheduleUpdateRequestDto;
import com.sparta.my_only_schedule_app.dto.response.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.entity.Schedule;
import com.sparta.my_only_schedule_app.exception.CommonException;
import com.sparta.my_only_schedule_app.exception.ExceptionCode;
import com.sparta.my_only_schedule_app.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 등록
     * @param resquestDto : 등록할 일정 정보를 받은 객체
     * @return 등록한 Schedule 객체
     */
    @Transactional
    public ScheduleResponseDto saveSchedule(ScheduleCreateRequestDto resquestDto) throws CommonException {
        log.trace("ScheduleService - saveSchedule() 메서드 실행");
        Schedule schedule = new Schedule(resquestDto);
        this.scheduleRepository.save(schedule);

        Schedule savedSchedule = this.scheduleRepository.findById(schedule.getId())
                                                        .orElseThrow(() ->
                                                                new CommonException(ExceptionCode.SAVE_FAILED)
                                                        );

        return new ScheduleResponseDto(savedSchedule);
    }


    /**
     * 일정 조회 by id (단건 조회)
     * @param id : 조회할 일정 고유 번호
     * @return 조회된 일정 객체
     * @throws IllegalArgumentException : 유효하지 않은 일정 고유 번호일 때, 발생
     */
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(long id) throws CommonException {
        log.trace("ScheduleService - getSchedule() 메서드 실행");
        Schedule schedule = this.scheduleRepository.findById(id)
                                    .orElseThrow(() ->
                                        new CommonException(ExceptionCode.INVALID_SC_ID, id)
                                    );

        return new ScheduleResponseDto(schedule);
    }

    /**
     * 일정 수정
     * @param id : 수정할 일정 고유 번호
     * @param requestDto : 수정할 일정 정보가 담긴 객체
     * @return 수정한 일정 정보가 담긴 객체
     * @throws IllegalArgumentException : 일정 고유 번호가 유효하지 않을 때, 발생
     */
    @Transactional
    public ScheduleResponseDto updateSchedule(long id, ScheduleUpdateRequestDto requestDto) throws CommonException {
        log.trace("ScheduleService - updateSchedule() 메서드 실행");
        Schedule schedule = this.scheduleRepository.findById(id)
                                                .orElseThrow(() ->
                                                        new CommonException(ExceptionCode.INVALID_SC_ID, id)
                                                );
        schedule.update(requestDto);

        Schedule updatedSchedule = this.scheduleRepository.findById(id)
                                                            .orElseThrow(() ->
                                                                    new CommonException(ExceptionCode.INVALID_SC_ID, id)
                                                            );

        return new ScheduleResponseDto(updatedSchedule);
    }
}
