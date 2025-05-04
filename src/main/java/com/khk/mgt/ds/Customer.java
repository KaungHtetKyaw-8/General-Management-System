package com.khk.mgt.ds;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;


import java.util.List;

@Entity
@Data
public class Customer extends Person {

    @OneToMany(mappedBy = "customer",cascade = CascadeType.PERSIST)
    private List<PointCard> pointCard;

    public Customer() {
    }

    public void addPointCard(PointCard pointCard) {
        pointCard.setCustomer(this);
        this.pointCard.add(pointCard);
    }
}
