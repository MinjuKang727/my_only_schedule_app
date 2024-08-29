package com.sparta.my_only_schedule_app.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
public class PagingRequestDto {
    @NotNull(message = "페이지 번호를 입력해 주십시오.")
    @Min(value = 0, message = "페이지 번호는 양의 정수이어야 합니다.")
    private Integer pageNumber;
    private Integer pageSize = 10;

    public PagingRequestDto(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public PagingRequestDto(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Pageable getPageable() {
        return PageRequest.of(pageNumber, pageSize, Sort.by("modifiedAt").descending());
    }
}
