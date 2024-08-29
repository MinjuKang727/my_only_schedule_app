package com.sparta.my_only_schedule_app.controller;

import com.sparta.my_only_schedule_app.dto.comment.request.CommentCreateRequestDto;
import com.sparta.my_only_schedule_app.dto.comment.request.CommentDeleteRequestDto;
import com.sparta.my_only_schedule_app.dto.comment.request.CommentUpdateRequestDto;
import com.sparta.my_only_schedule_app.dto.comment.response.CommentResponseDto;
import com.sparta.my_only_schedule_app.exception.CommonException;
import com.sparta.my_only_schedule_app.service.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/schedules")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 등록
     * @pathvariable scheduleId : 등록할 댓글이 등록되어 있는 일정 고유 번호
     * @param requestDto : 댓글 등록 정보를 받는 객체
     * @return 등록된 댓글 객체
     */
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody @Valid CommentCreateRequestDto requestDto) throws CommonException {
        return ResponseEntity.ok(this.commentService.saveComment(scheduleId, requestDto));
    }


    /**
     * 댓글 조회(단건 조회)
     * @pathvariable scheduleId : 조회하고 싶은 댓글이 등록되어 있는 일정 고유 번호
     * @param commentId : 조회할 댓글 고유 번호
     * @return 조회된 댓글 정보를 담은 객체
     */
    @GetMapping("/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) throws CommonException {
        return ResponseEntity.ok(this.commentService.getComment(commentId));
    }

    /**
     * 댓글 조회(전체 조회)
     * @return 조회된 댓글 정보를 담은 리스트
     */
    @GetMapping("/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId) throws CommonException {
        return ResponseEntity.ok(this.commentService.getComments(scheduleId));
    }

    /**
     * 댓글 수정
     * @pathvariable scheduleId : 수정할 댓글이 등록되어 있는 일정 고유 번호
     * @param requestDto : 수정할 댓글 정보를 담은 객체
     * @return CommentResponseDto : 수정된 댓글 정보를 담은 객체
     */
    @PutMapping("/{scheduleId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequestDto requestDto) throws CommonException {
        return ResponseEntity.ok(this.commentService.updateComment(commentId, requestDto));
    }

    /**
     * 댓글 삭제
     * @param commentId : 삭제할 댓글 고유 번호
     * @param requestDto : 현재 댓글을 삭제하고자 하는 유저명을 담고 있는 객체
     * @return 댓글 고유 번호
     * @throws CommonException : commentId의 댓글이 존재하지 않거나, 현재 유저가 댓글 작성자가 아닌 경우 발생
     */
    @DeleteMapping("/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Long> deleteComment(@PathVariable Long commentId, @RequestBody @Valid CommentDeleteRequestDto requestDto) throws CommonException {
        return ResponseEntity.ok(this.commentService.deleteComment(commentId, requestDto));
    }
}
