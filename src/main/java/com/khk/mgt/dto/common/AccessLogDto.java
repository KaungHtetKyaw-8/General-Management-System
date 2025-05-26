package com.khk.mgt.dto.common;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogDto {

    private Long accessKeyId;

    private String accessUser;

    private LocalDateTime accessedAt;

    private String remoteIp;

    private Boolean isActive;
}
