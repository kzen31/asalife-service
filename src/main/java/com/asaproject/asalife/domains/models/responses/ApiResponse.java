package com.asaproject.asalife.domains.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponse{
//public class ApiResponse<T> {
    @Builder.Default
    private String status = "Success";
    private String message;
//    private T data;
//    private Integer page;
//    private Integer totalPages;
//    private Long totalElements;
}
