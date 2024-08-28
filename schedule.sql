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

# 담당자명 칼럼 이름 변경 (NAME -> MANAGER)
ALTER TABLE SCHEDULE.SCHEDULE CHANGE NAME MANAGER VARCHAR(20) NOT NULL;

# 칼럼별로 대/소문자가 들쭉날쭉해서 소문자로 일치
ALTER TABLE schedule.schedule CHANGE MANAGER manager VARCHAR(20) NOT NULL;
ALTER TABLE schedule.schedule CHANGE PW pw VARCHAR(20) NOT NULL;

###################################################################################
# manager 테이블 생성을 위한 schedule 테이블 삭제
TRUNCATE TABLE SCHEDULE.SCHEDULE;

# schedule 테이블 칼럼명 및 타입 변경, 제약조건 추가
ALTER TABLE SCHEDULE.SCHEDULE CHANGE id schedule_id VARCHAR(20) NOT NULL;
ALTER TABLE SCHEDULE.SCHEDULE CHANGE manager manager_id INT(10) NOT NULL;

# manager 테이블 생성
CREATE TABLE SCHEDULE.MANAGER(
id INT(10) NOT NULL COMMENT "아이디",
name VARCHAR(20) NOT NULL COMMENT "담당자명",
email VARCHAR(100) UNIQUE COMMENT "이메일",
submit_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "작성일",
edit_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT "수정일",
CONSTRAINT manager_pk PRIMARY KEY(id)
);

# id칼럼 schedule 테이블의 manager_id와 일치하도록 칼럼명 변경
ALTER TABLE SCHEDULE.manager CHANGE id manager_id INT(10) NOT NULL;

# schedule 테이블의 manager_id 칼럼에 외래키 제약조건 추가
ALTER TABLE SCHEDULE.schedule ADD CONSTRAINT schedule_fk FOREIGN KEY(manager_id)
REFERENCES SCHEDULE.manager (manager_id) ON UPDATE CASCADE;

# manager테이블 manager_id 칼럼 auto_increment 추가가 바로 안되어서 schedule 테이블 외래키, manager 테이블 primary key 삭제
ALTER TABLE schedule.schedule DROP FOREIGN KEY schedule_fk;
ALTER TABLE schedule.manager DROP PRIMARY KEY;

# manager 테이블 manager_id에 auto_increment, pk 제약조건 추가
ALTER TABLE SCHEDULE.manager MODIFY manager_id INT(10) AUTO_INCREMENT PRIMARY KEY;

# schedule 테이블 manager_id에 fk 제약조건 추가
ALTER TABLE schedule.schedule ADD FOREIGN KEY(manager_id) REFERENCES schedule.manager(manager_id);

# manager 테이블 pw 칼럼 추가
ALTER TABLE schedule.manager ADD COLUMN pw VARCHAR(20) NOT NULL AFTER NAME;

# schedule 테이블 schedule_id 칼럼에 auto_increment 추가
ALTER TABLE schedule.schedule MODIFY schedule_id INT(10) AUTO_INCREMENT;

# manager에서 담당자를 삭제할 때, 관련 일정도 함께 삭제되도록 테이블 수정
ALTER TABLE schedule.schedule DROP FOREIGN KEY schedule_fk;
ALTER TABLE schedule.schedule ADD CONSTRAINT schedule_fk FOREIGN KEY(manager_id) REFERENCES schedule.manager(manager_id) ON DELETE CASCADE ON UPDATE CASCADE;

# 할일은 최대 200자 이내로 제한하라는 추가요구사항으로 테이블 칼럼 수정
ALTER TABLE schedule.schedule MODIFY todo VARCHAR(200) NOT NULL;