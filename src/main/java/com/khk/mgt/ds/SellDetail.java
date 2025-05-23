package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SellDetail {

    @EmbeddedId
    private SellDetailId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("ordersId")
    @JoinColumn(name = "orders_id")
    private Orders orders;

    private Long quantity;

    public SellDetail() {
    }
}
