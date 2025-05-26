package com.khk.mgt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AccessKeyDto {

    private Long id;

    private String hashedKey;

    private String secretKey;

    private LocalDateTime accessedAt;

    private String remoteIp;

    private boolean active;
}
