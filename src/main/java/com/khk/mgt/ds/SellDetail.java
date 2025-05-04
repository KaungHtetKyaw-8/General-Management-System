package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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

    private int quantity;
    private double price;

    public SellDetail() {
    }
}
