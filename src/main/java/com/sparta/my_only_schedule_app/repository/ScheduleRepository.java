package com.sparta.my_only_schedule_app.repository;

import com.sparta.my_only_schedule_app.dto.ScheduleRequestDto;
import com.sparta.my_only_schedule_app.dto.ScheduleResponseDto;
import com.sparta.my_only_schedule_app.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    @Autowired
    ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 등록
    public Schedule save(Schedule schedule) throws IllegalArgumentException{
        KeyHolder keyHolder = new GeneratedKeyHolder();

        if (schedule.getManager_id() == 0) {
            throw new IllegalArgumentException("담당자 고유 식별자는 필수 입력사항입니다.");
        } else if (schedule.getTodo() == null) {
            throw new IllegalArgumentException("할일은 필수 입력 사항입니다.");
        } else if (schedule.getPw() == null) {
            throw new IllegalArgumentException("비밀번호는 필수 입력 사항입니다.");
        }

        String sql = "INSERT INTO schedule.schedule(todo, manager_id, pw) VALUES (?, ?, ?)";

        this.jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, schedule.getTodo());
            preparedStatement.setLong(2, schedule.getManager_id());
            preparedStatement.setString(3, schedule.getPw());

            return preparedStatement;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        schedule.setSchedule_id(id);

        return schedule;
    }

    // 일정 목록 조회 by 수정일 or 담당자명
    public List<ScheduleResponseDto> getSchedules(String edit_date, String name) {
        // DB 조회
        if (edit_date == null && name == null) {
            return this.getSchedules();
        } else if (edit_date != null && name == null) {
            String sql = "SELECT s.schedule_id, s.todo, m.name, s.submit_date, s.edit_date FROM schedule.schedule s, schedule.manager m WHERE s.manager_id = m.manager_id AND s.edit_date = ?";
            return getSchedulesByData(edit_date, sql);
        } else if (edit_date == null && name != null) {
            String sql = "SELECT s.schedule_id, s.todo, m.name, s.submit_date, s.edit_date FROM schedule.schedule s, schedule.manager m WHERE s.manager_id = m.manager_id AND m.name = ?";
            return getSchedulesByData(name, sql);
        } else {
            String sql = "SELECT s.schedule_id, s.todo, m.name, s.submit_date, s.edit_date FROM schedule.schedule s, schedule.manager m WHERE s.manager_id = m.manager_id AND s.edit_date = ? AND m.name = ?";
            return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
                // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long schedule_id = rs.getLong("schedule_id");
                String todo = rs.getString("todo");
                String name2 = rs.getString("name");
                String submit_date = rs.getString("submit_date");
                String edit_date2 = rs.getString("edit_date");
                return new ScheduleResponseDto(schedule_id, todo, name2, submit_date, edit_date2);
            }, edit_date, name);
        }
    }

    // sql에서 지정해 준 칼럼 값이 data인 행 조회
    private List<ScheduleResponseDto> getSchedulesByData(String data, String sql) {
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
            Long schedule_id = rs.getLong("schedule_id");
            String todo = rs.getString("todo");
            String name = rs.getString("name");
            String submit_date = rs.getString("submit_date");
            String edit_date = rs.getString("edit_date");
            return new ScheduleResponseDto(schedule_id, todo, name, submit_date, edit_date);
        }, data);
    }

    // 모든 일정 조회
    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회
        String sql = "SELECT s.schedule_id, s.todo, m.name, s.submit_date, s.edit_date FROM schedule.schedule s, schedule.manager m WHERE s.manager_id = m.manager_id";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
            Long schedule_id = rs.getLong("schedule_id");
            String todo = rs.getString("todo");
            String name2 = rs.getString("name");
            String submit_date = rs.getString("submit_date");
            String edit_date2 = rs.getString("edit_date");
            return new ScheduleResponseDto(schedule_id, todo, name2, submit_date, edit_date2);
        });
    }

    // 일정 수정
    public void update(long schedule_id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule.schedule SET todo = ?, manager_id = ?, edit_date = CURRENT_TIMESTAMP WHERE schedule_id = ?";
        this.jdbcTemplate.update(sql, requestDto.getTodo(), requestDto.getManager_id(), schedule_id);
    }

    // 일정 삭제
    public void delete(long schedule_id) {
        String sql = "DELETE FROM schedule.schedule WHERE schedule_id = ?";
        this.jdbcTemplate.update(sql, schedule_id);
    }

    // ID로 ScheduleResponseDto 객체 GETTER
    public ScheduleResponseDto getSchedule(long schedule_id) {
        // DB 조회
        String sql = "SELECT s.schedule_id, s.todo, m.name, s.submit_date, s.edit_date FROM SCHEDULE.SCHEDULE s, schedule.manager m WHERE s.manager_id = m.manager_id AND s.schedule_id = ?";

        return this.jdbcTemplate.queryForObject(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
                // SQL 의 결과로 받아온 Schedule 데이터를 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long schedule_id = rs.getLong("schedule_id");
                String todo = rs.getString("todo");
                String name = rs.getString("name");
                String submit_date = rs.getString("submit_date");
                String edit_date = rs.getString("edit_date");
                return new ScheduleResponseDto(schedule_id, todo, name, submit_date, edit_date);
            }
        }, schedule_id);
    }

    // ID 존재 여부 체크
    public Boolean checkId(long schedule_id) throws IllegalArgumentException {
        // DB 조회
        String sql = "SELECT COUNT(*) ok FROM schedule.schedule WHERE schedule_id = ? ";

        Boolean ok = this.jdbcTemplate.queryForObject(sql, new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
                // SQL 의 결과로 받아온 Schedule 데이터를 Boolean 타입으로 변환해줄 메서드
                try {
                    int exist = rs.getInt("ok");
                    return exist == 1;
                } catch (EmptyResultDataAccessException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }, schedule_id);

        if (!ok) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다.");
        }

        return ok;
    }

    // ID, PW 일치 여부 조회
    public Boolean checkIdPw(long schedule_id, String pw) throws IllegalArgumentException {
        Boolean ok = false;
        try {
            ok = this.checkId(schedule_id);
        } catch (IllegalArgumentException e) {
            throw e;
        }


        if (ok) {
            String sql = "SELECT COUNT(*) ok FROM schedule.schedule WHERE schedule_id = ? AND pw = ?";
                ok = this.jdbcTemplate.queryForObject(sql, new RowMapper<Boolean>() {
                    @Override
                    public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
                        // SQL 의 결과를 boolean 타입으로 변환해줄 메서드
                        try {
                            int exist = rs.getInt("ok");
                            return exist == 1;
                        } catch (EmptyResultDataAccessException e) {
                            System.out.println(e.getMessage());
                            return false;
                        }
                    }
                }, schedule_id, pw);
        }

        if (Boolean.FALSE.equals(ok)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return ok;
    }
}
