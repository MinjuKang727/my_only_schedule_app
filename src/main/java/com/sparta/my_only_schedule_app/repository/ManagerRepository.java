package com.sparta.my_only_schedule_app.repository;

import com.sparta.my_only_schedule_app.dto.ManagerRequestDto;
import com.sparta.my_only_schedule_app.dto.ManagerResponseDto;
import com.sparta.my_only_schedule_app.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class ManagerRepository {
    private final JdbcTemplate jdbcTemplate;

    // 생성자
    @Autowired
    ManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 담당자 등록
    public Manager save(Manager manager) throws IllegalArgumentException{
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO schedule.manager(name, email, pw) VALUES (?, ?, ?)";

        if (manager.getName() == null) {
            throw new IllegalArgumentException("담당자명은 필수 입력사항입니다.");
        } else if (manager.getPw() == null) {
            throw new IllegalArgumentException("비밀번호는 필수 입력사항입니다.");
        } else if (manager.getEmail() != null && !Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", manager.getEmail())) {
            throw new IllegalArgumentException("알 수 없는 이메일 주소를 입력하셨습니다.");
        }

        this.jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, manager.getName());
            preparedStatement.setString(2, manager.getEmail());
            preparedStatement.setString(3, manager.getPw());

            return preparedStatement;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        manager.setManager_id(id);

        return manager;
    }

    // 담당자 목록 조회 by email or 담당자명
    public List<ManagerResponseDto> getManagers(String name, String email) {
        // DB 조회
        if (email == null && name == null) {
            return this.getManagers();
        } else if (email != null && name == null) {
            String sql = "SELECT manager_id, name, email, submit_date, edit_date FROM schedule.manager WHERE email = ? ";
            return getManagersByData(email, sql);
        } else if (email == null && name != null) {
            String sql = "SELECT manager_id, name, email, submit_date, edit_date FROM schedule.manager WHERE name = ?";
            return getManagersByData(name, sql);
        } else {
            String sql = "SELECT manager_id, name, email, submit_date, edit_date FROM schedule.manager WHERE name = ? AND email = ?";
            return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
                // SQL 의 결과로 받아온 Manager 데이터들을 ManagerResponseDto 타입으로 변환해줄 메서드
                Long manager_id = rs.getLong("manager_id");
                String name2 = rs.getString("name");
                String email2 = rs.getString("email");
                String submit_date = rs.getString("submit_date");
                String edit_date2 = rs.getString("edit_date");
                return new ManagerResponseDto(manager_id, name2, email2, submit_date, edit_date2);
            }, name, email);
        }
    }

    // sql에서 지정해 준 칼럼 값이 data인 행 조회
    private List<ManagerResponseDto> getManagersByData(String data, String sql) {
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            // SQL 의 결과로 받아온 Manager 데이터들을 ManagerResponseDto 타입으로 변환해줄 메서드
            Long manager_id = rs.getLong("manager_id");
            String name2 = rs.getString("name");
            String email = rs.getString("email");
            String submit_date = rs.getString("submit_date");
            String edit_date2 = rs.getString("edit_date");
            return new ManagerResponseDto(manager_id, name2, email, submit_date, edit_date2);
        }, data);
    }

    // 모든 담당자 조회
    public List<ManagerResponseDto> getManagers() {
        // DB 조회
        String sql = "SELECT manager_id, name, email, submit_date, edit_date FROM schedule.manager";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            // SQL 의 결과로 받아온 Manager 데이터들을 ManagerResponseDto 타입으로 변환해줄 메서드
            Long manager_id = rs.getLong("manager_id");
            String name2 = rs.getString("name");
            String email = rs.getString("email");
            String submit_date = rs.getString("submit_date");
            String edit_date2 = rs.getString("edit_date");
            return new ManagerResponseDto(manager_id, name2, email, submit_date, edit_date2);
        });
    }

    // 담당자 수정
    public void update(long manager_id, ManagerRequestDto requestDto) throws IllegalArgumentException {
        if (requestDto.getEmail() != null && !Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", requestDto.getEmail())) {
            throw new IllegalArgumentException("알 수 없는 이메일 주소를 입력하셨습니다.");
        }

        String sql = "UPDATE schedule.manager SET name = ?, email = ?, edit_date = CURRENT_TIMESTAMP WHERE manager_id = ?";
        this.jdbcTemplate.update(sql, requestDto.getName(), requestDto.getEmail(), manager_id);
    }

    // 담당자 삭제
    public void delete(long manager_id) {
        String sql = "DELETE FROM schedule.manager WHERE manager_id = ?";
        this.jdbcTemplate.update(sql, manager_id);
    }

    // ID로 ManagerResponseDto 객체 GETTER
    public ManagerResponseDto getManager(long manager_id) {
        // DB 조회
        String sql = "SELECT manager_id, name, email, submit_date, edit_date FROM schedule.manager WHERE manager_id = ?";

        return this.jdbcTemplate.queryForObject(sql, new RowMapper<ManagerResponseDto>() {
            @Override
            public ManagerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
                // SQL 의 결과로 받아온 Manager 데이터를 ManagerResponseDto 타입으로 변환해줄 메서드
                Long manager_id2 = rs.getLong("manager_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String submit_date = rs.getString("submit_date");
                String edit_date = rs.getString("edit_date");
                return new ManagerResponseDto(manager_id2, name, email, submit_date, edit_date);
            }
        }, manager_id);
    }

    // ID 존재 여부 체크
    public Boolean checkId(long manager_id) throws IllegalArgumentException {
        // DB 조회
        String sql = "SELECT COUNT(*) ok FROM schedule.manager WHERE manager_id = ? ";

        Boolean ok = this.jdbcTemplate.queryForObject(sql, new RowMapper<Boolean>() {
            @Override
            public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException, EmptyResultDataAccessException {
                // SQL 의 결과로 받아온 Manager 데이터를 Boolean 타입으로 변환해줄 메서드
                try {
                    int exist = rs.getInt("ok");
                    return exist == 1;
                } catch (EmptyResultDataAccessException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        }, manager_id);

        if (!ok) {
            throw new IllegalArgumentException("해당 ID의 담당자가 존재하지 않습니다.");
        }

        return ok;
    }

    // ID, PW 일치 여부 조회
    public Boolean checkIdPw(long manager_id, String pw) throws IllegalArgumentException {
        Boolean ok = false;
        try {
            ok = this.checkId(manager_id);
        } catch (IllegalArgumentException e) {
            throw e;
        }


        if (ok) {
            String sql = "SELECT COUNT(*) ok FROM schedule.manager WHERE manager_id = ? AND pw = ?";
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
            }, manager_id, pw);
        }

        if (!ok) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return ok;
    }
}
