package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class PointCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date registrationDate;
    private Long points;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private PointCardCategory category;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public PointCard() {
    }

    @Override
    public String toString() {
        return "PointCard{" +
                "id=" + id +
                ", registrationDate=" + registrationDate +
                ", points=" + points +
                ", category=" + category +
                '}';
    }
}
