package com.balenciaga.DTO.Response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse<T> {
    private T data;
    String message;
}
