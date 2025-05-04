package com.khk.mgt.ds;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class SellDetailId implements Serializable {

    private Long productId;
    private Long ordersId;

    public SellDetailId() {
    }
}
