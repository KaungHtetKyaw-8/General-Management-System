package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Vendor extends Person {

    private String companyName;

    @OneToMany(mappedBy = "vendor")
    private List<Product> product;

    public Vendor() {
    }

    public void addProduct(Product product) {
        product.setVendor(this);
        this.product.add(product);
    }
}
