package com.sparta.my_only_schedule_app.service;

import com.sparta.my_only_schedule_app.dto.ManagerRequestDto;
import com.sparta.my_only_schedule_app.dto.ManagerResponseDto;
import com.sparta.my_only_schedule_app.entity.Manager;
import com.sparta.my_only_schedule_app.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;

    @Autowired
    ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    // 일정 등록
    public ManagerResponseDto save(ManagerRequestDto resquestDto) {
        Manager manager = new Manager(resquestDto);
        Manager saveManager = null;

        try {
            saveManager = this.managerRepository.save(manager);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        if (saveManager != null) {
            return this.managerRepository.getManager(saveManager.getManager_id());
        }

        return null;
    }

    // 담당자 조회 by id (단건 조회)
    public ManagerResponseDto getManager(long manager_id) {
        boolean ok = false;

        try {
            ok = this.managerRepository.checkId(manager_id);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (ok) {
            return this.managerRepository.getManager(manager_id);
        }

        return null;
    }

    // 담당자 목록 조회 by 담당자명, 이메일 (다건 조회)
    public List<ManagerResponseDto> getManagers(String name, String email) {
        return this.managerRepository.getManagers(name, email);
    }

    // 일정 수정
    public ManagerResponseDto update(long manager_id, ManagerRequestDto requestDto) {
        boolean ok = this.managerRepository.checkIdPw(manager_id, requestDto.getPw());

        if (ok) {
            this.managerRepository.update(manager_id, requestDto);

            return this.managerRepository.getManager(manager_id);
        }

        return null;
    }

    // 일정 삭제
    public long delete(long manager_id, String pw) {
        Boolean ok = false;

        try {
            ok = this.managerRepository.checkIdPw(manager_id, pw);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (ok) {
            this.managerRepository.delete(manager_id);

            return manager_id;
        } else {
            return -1;
        }
    }
}