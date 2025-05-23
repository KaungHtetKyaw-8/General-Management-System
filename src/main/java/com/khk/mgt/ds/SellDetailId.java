package com.khk.mgt.ds;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SellDetailId implements Serializable {

    private Long productId;
    private Long ordersId;

    public SellDetailId() {
    }

    public SellDetailId(Long productId, Long ordersId) {
        this.productId = productId;
        this.ordersId = ordersId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SellDetailId that)) return false;
        return Objects.equals(productId, that.productId) && Objects.equals(ordersId, that.ordersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, ordersId);
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Long ordersId) {
        this.ordersId = ordersId;
    }
}
