package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
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
