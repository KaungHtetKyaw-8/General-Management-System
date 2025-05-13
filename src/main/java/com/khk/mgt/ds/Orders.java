package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.PERSIST)
    private List<SellDetail> sellDetail = new ArrayList<SellDetail>();

    private Date orderDate;

    public Orders() {
    }

    public void addSellDetail(SellDetail sellDetail) {
        sellDetail.setOrders(this);
        this.sellDetail.add(sellDetail);
    }
}
