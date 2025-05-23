package com.khk.mgt.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PosPointCardDto {

    private Long customerId;
    private String cardType;
    private Long cardId;

}
