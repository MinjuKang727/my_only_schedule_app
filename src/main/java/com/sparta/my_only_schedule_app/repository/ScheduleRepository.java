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
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO SCHEDULE.SCHEDULE(TODO, NAME, PW) VALUES ( ?, ?, ?)";
        try {
            this.jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, schedule.getTodo());
                preparedStatement.setString(2, schedule.getName());
                preparedStatement.setString(3, schedule.getPw());

                return preparedStatement;
            }, keyHolder);

            long id = keyHolder.getKey().longValue();
            schedule.setId(id);
        } catch (Exception e) {
            System.out.println("할일, 담당자명, 비밀번호는 필수 입력사항입니다.");
            return null;
        }
        return schedule;
    }

    // 일정 목록 조회 by 수정일 or 담당자명
    public List<ScheduleResponseDto> getScheduleList(String edit_date, String name) {
        // DB 조회
        if (edit_date == null && name == null) {
            return this.getAllSchedule();
        } else if (edit_date != null && name == null) {
            String sql = "SELECT ID, TODO, NAME, SUBMIT_DATE, EDIT_DATE FROM SCHEDULE.SCHEDULE WHERE EDIT_DATE = ?";
            return getScheduleResponseDtos(edit_date, sql);
        } else if (edit_date == null && name != null) {
            String sql = "SELECT ID, TODO, NAME, SUBMIT_DATE, EDIT_DATE FROM SCHEDULE.SCHEDULE WHERE NAME = ?";
            return getScheduleResponseDtos(name, sql);
        } else {
            String sql = "SELECT ID, TODO, NAME, SUBMIT_DATE, EDIT_DATE FROM SCHEDULE.SCHEDULE WHERE EDIT_DATE = ? AND NAME = ?";
            return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
                // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String todo = rs.getString("todo");
                String name2 = rs.getString("name");
                String submit_date = rs.getString("submit_date");
                String edit_date2 = rs.getString("edit_date");
                return new ScheduleResponseDto(id, todo, name2, submit_date, edit_date2);
            }, edit_date, name);
        }
    }

    // sql에서 지정해 준 칼럼 값이 data인 행 조회
    private List<ScheduleResponseDto> getScheduleResponseDtos(String data, String sql) {
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
            Long id = rs.getLong("id");
            String todo = rs.getString("todo");
            String name = rs.getString("name");
            String submit_date = rs.getString("submit_date");
            String edit_date = rs.getString("edit_date");
            return new ScheduleResponseDto(id, todo, name, submit_date, edit_date);
        }, data);
    }

    // 모든 일정 조회
    public List<ScheduleResponseDto> getAllSchedule() {
        // DB 조회
        String sql = "SELECT ID, TODO, NAME, SUBMIT_DATE, EDIT_DATE FROM SCHEDULE.SCHEDULE";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
            Long id = rs.getLong("id");
            String todo = rs.getString("todo");
            String name2 = rs.getString("name");
            String submit_date = rs.getString("submit_date");
            String edit_date2 = rs.getString("edit_date");
            return new ScheduleResponseDto(id, todo, name2, submit_date, edit_date2);
        });
    }

    // 일정 수정
    public void update(long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE SCHEDULE.SCHEDULE SET TODO = ?, NAME = ?, EDIT_DATE = CURRENT_TIMESTAMP WHERE ID = ?";
        this.jdbcTemplate.update(sql, requestDto.getTodo(), requestDto.getName(), id);
    }

    // 일정 삭제
    public void delete(long id) {
        String sql = "DELETE FROM SCHEDULE.SCHEDULE WHERE ID = ?";
        this.jdbcTemplate.update(sql, id);
    }

    // ID로 SchduleResponseDto 객체 GETTER
    public ScheduleResponseDto getScheduleResponseById(long id) {
        // DB 조회
        String sql = "SELECT ID, PW, TODO, NAME, SUBMIT_DATE, EDIT_DATE FROM SCHEDULE.SCHEDULE WHERE ID = ?";

        return this.jdbcTemplate.queryForObject(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
                // SQL 의 결과로 받아온 Schedule 데이터를 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String todo = rs.getString("todo");
                String name = rs.getString("name");
                String submit_date = rs.getString("submit_date");
                String edit_date = rs.getString("edit_date");
                return new ScheduleResponseDto(id, todo, name, submit_date, edit_date);
            }
        }, id);
    }

    // ID 존재 여부 체크
    public Boolean checkId(long id) throws IllegalArgumentException {
        // DB 조회
        String sql = "SELECT COUNT(*) ok FROM SCHEDULE.SCHEDULE WHERE ID = ? ";

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
        }, id);

        if (!ok) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다.");
        }

        return ok;
    }

    // ID, PW 일치 여부 조회
    public Boolean checkIdPw(long id, String pw) throws IllegalArgumentException {
        Boolean ok = false;
        try {
            ok = this.checkId(id);
        } catch (IllegalArgumentException e) {
            throw e;
        }


        if (ok) {
            String sql = "SELECT COUNT(*) ok FROM SCHEDULE.SCHEDULE WHERE ID = ? AND PW = ?";
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
                }, id, pw);
        }

        if (Boolean.FALSE.equals(ok)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return ok;
    }
}
