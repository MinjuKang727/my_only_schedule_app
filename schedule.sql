# 데이터베이스 생성
create database schedule;

# 테이블 생성
create table schedule.schedule(
id int(10) auto_increment comment '아이디',
todo varchar(100) not null comment '할 일',
name varchar(20) not null comment '담당자명',
pw int(10) not null comment '비밀번호',
submit_date date not null comment '작성일',
edit_date date not null comment '수정일',
constraint schedule_pk primary key(id)
);

# 비밀번호 칼럼 타입 변경 (int -> varchar)
truncate table schedule.schedule;
ALTER TABLE schedule.schedule CHANGE PW PW VARCHAR(20) NOT NULL;

# 작성/수정일 칼럼 타입 변경 및 default 값 설정
ALTER TABLE schedule.schedule CHANGE submit_date submit_date DATETIME DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE schedule.schedule CHANGE edit_date edit_date  DATETIME DEFAULT CURRENT_TIMESTAMP;
