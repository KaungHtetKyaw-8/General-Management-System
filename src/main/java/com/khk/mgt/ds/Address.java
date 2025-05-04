package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apartment;
    private String street;
    private String city;
    private String cuntry;

    public Address() {
    }
}
