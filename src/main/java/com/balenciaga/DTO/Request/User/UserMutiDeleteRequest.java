package com.balenciaga.DTO.Request.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserMutiDeleteRequest {
    private List<String> id;
}
