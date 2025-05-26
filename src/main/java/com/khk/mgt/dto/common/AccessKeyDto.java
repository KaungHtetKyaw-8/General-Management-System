package com.khk.mgt.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccessKeyDto {

    private Long id;

    @NotBlank
    private String userName;
    @NotBlank
    private String secretKey;

    private String hashedKey;

    private boolean active;
}
