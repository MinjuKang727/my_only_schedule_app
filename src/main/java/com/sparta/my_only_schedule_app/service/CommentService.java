package com.sparta.my_only_schedule_app.service;

import com.sparta.my_only_schedule_app.dto.comment.request.CommentCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.comment.request.CommentDeleteRequestDto;
import com.sparta.my_only_schedule_app.dto.comment.request.CommentUpdateRequestDto;
import com.sparta.my_only_schedule_app.dto.comment.response.CommentResponseDto;
import com.sparta.my_only_schedule_app.entity.Comment;
import com.sparta.my_only_schedule_app.entity.Schedule;
import com.sparta.my_only_schedule_app.exception.CommonException;
import com.sparta.my_only_schedule_app.exception.ExceptionCode;
import com.sparta.my_only_schedule_app.repository.CommentRepository;
import com.sparta.my_only_schedule_app.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final EntityManager em;
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글 등록
     * @param resquestDto : 등록할 댓글 정보를 받은 객체
     * @return 등록한 Schedule 객체
     */
    @Transactional
    public CommentResponseDto saveComment(Long scheduleId, CommentCreateRequestDto resquestDto) throws CommonException {
        log.trace("CommentService - saveComment() 메서드 실행");
        Schedule schedule = this.scheduleRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new CommonException(ExceptionCode.INVALID_SC_ID)
                );

        Comment comment = new Comment(resquestDto);
        comment.setSchedule(schedule);
        this.commentRepository.saveAndFlush(comment);

        Comment savedComment = em.find(Comment.class, comment.getCommentId());

        return new CommentResponseDto(savedComment);
    }


    /**
     * 댓글 조회 by commentId (단건 조회)
     * @param commentId : 조회할 댓글 고유 번호
     * @return 조회된 댓글 객체
     * @throws IllegalArgumentException : 유효하지 않은 댓글 고유 번호일 때, 발생
     */
    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long commentId) throws CommonException {
        log.trace("CommentService - getComment() 메서드 실행");
        Comment comment = this.commentRepository.findCommentByCommentId(commentId)
                .orElseThrow(() ->
                        new CommonException(ExceptionCode.NO_RESULT)
                );

        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long scheduleId) throws CommonException {
        log.trace("CommentService - getComments() 메서드 실행");
        Optional<List<Comment>> commentList = this.commentRepository.findCommentsByScheduleId(scheduleId);

        if(commentList.isEmpty()) {
            throw new CommonException(ExceptionCode.NO_RESULT);
        }

        return commentList.get().stream().map(CommentResponseDto::new).toList();
    }

    /**
     * 댓글 수정
     * @param requestDto : 수정할 댓글 정보가 담긴 객체
     * @return 수정한 댓글 정보가 담긴 객체
     * @throws IllegalArgumentException : 댓글 고유 번호가 유효하지 않을 때, 발생
     */
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto requestDto) throws CommonException {
        log.trace("CommentService - updateComment() 메서드 실행");

        Comment comment = this.commentRepository.findCommentByCommentId(commentId)
                .orElseThrow(() ->
                        new CommonException(ExceptionCode.INVALID_CO_ID)
                );
        if (!comment.getCreaterName().equals(requestDto.getCreaterName())) {
            throw new CommonException(ExceptionCode.INVALID_CO_CREATER);
        }

        comment.setContents(requestDto.getContents());
        em.flush();

        Comment updatedComment = em.find(Comment.class, comment.getCommentId());

        return new CommentResponseDto(updatedComment);
    }

    /**
     * 댓글 삭제
     * @param commentId : 삭제할 댓글 고유 번호
     * @param requestDto : 삭제할 댓글의 작성 유저 검증을 위한 데이터(작성 유저명)를 담은 객체
     * @return 성공 : 댓글 고유 번호/ 실패 : -1;
     * @throws CommonException : 존재하지 않는 댓글 고유번호이거나 댓글 작성 유저명과 요청한 requestDto에 담긴 작성 유저명이 불일치 할 때, 발생
     */
    @Transactional
    public Long deleteComment(Long commentId, CommentDeleteRequestDto requestDto) throws CommonException {
        log.trace("CommentService - deleteComment() 메서드 실행");
        Comment comment = this.commentRepository.findCommentByCommentId(commentId)
                .orElseThrow(() ->
                        new CommonException(ExceptionCode.INVALID_CO_ID)
                );

        if (!comment.getCreaterName().equals(requestDto.getCreaterName())) {
            throw new CommonException(ExceptionCode.INVALID_CO_CREATER);
        }

        em.remove(comment);

        return commentId;
    }
}
