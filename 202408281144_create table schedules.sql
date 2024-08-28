create table schedules (
       id bigint not null auto_increment,
       creater_name varchar(20) not null,
       title varchar(100) not null,
       to_do varchar(200) not null,
       created_at datetime(6) not null,
       modified_at datetime(6) not null,
       primary key (id)
)