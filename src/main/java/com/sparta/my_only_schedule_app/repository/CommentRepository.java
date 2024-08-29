package com.sparta.my_only_schedule_app.repository;

import com.sparta.my_only_schedule_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c where c.commentId = :commentId")
    Optional<Comment> findCommentByCommentId(Long commentId);

    @Query(value = "select c from Comment c where c.schedule.scheduleId = :scheduleId")
    Optional<List<Comment>> findCommentsByScheduleId(Long scheduleId);
}
