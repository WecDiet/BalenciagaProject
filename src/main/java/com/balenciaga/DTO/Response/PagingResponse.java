package com.balenciaga.DTO.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse<T> {
    private T data;
    private List<String> message;
    private int totalPage;
    private Long totalCount;
}
