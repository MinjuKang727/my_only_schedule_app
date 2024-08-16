package com.sparta.my_only_schedule_app.controller;

import com.sparta.my_only_schedule_app.dto.ManagerRequestDto;
import com.sparta.my_only_schedule_app.dto.ManagerResponseDto;
import com.sparta.my_only_schedule_app.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ManagerController {
    private final ManagerService managerService;

    // 생성자
    @Autowired
    ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    // 일정 등록
    @PostMapping("/managers")
    public ManagerResponseDto createManager(@RequestBody ManagerRequestDto requestDto) {
        ManagerResponseDto responseDto = null;
        try {
            responseDto = this.managerService.save(requestDto);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return responseDto;
    }

    // 일정 조회
    @GetMapping("/managers/{manager_id}")
    public ManagerResponseDto getManager(@PathVariable long manager_id) {
        return this.managerService.getManager(manager_id);
    }

    // 일정 목록 조회
    @GetMapping("/managers")
    public List<ManagerResponseDto> getManagers(@RequestParam(required = false) String name, @RequestParam(required = false) String email) {
        return this.managerService.getManagers(name, email);
    }

    // 일정 수정
    @PutMapping("/managers/{manager_id}")
    public ManagerResponseDto editManager(@PathVariable long manager_id, @RequestBody ManagerRequestDto requestDto) {
        return this.managerService.update(manager_id, requestDto);
    }

    // 일정 삭제
    @DeleteMapping("/managers/{manager_id}")
    public long deleteManager(@PathVariable long manager_id, @RequestBody ManagerRequestDto requestDto) {
        return this.managerService.delete(manager_id, requestDto.getPw());
    }
}