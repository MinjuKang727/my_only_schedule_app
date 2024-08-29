package com.sparta.my_only_schedule_app.repository;

import com.sparta.my_only_schedule_app.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
