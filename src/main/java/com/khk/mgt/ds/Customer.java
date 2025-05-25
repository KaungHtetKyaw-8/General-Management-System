package com.khk.mgt.ds;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Customer extends Person {

    @OneToMany(mappedBy = "customer",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<PointCard> pointCard;

    public Customer() {
    }

    public void addPointCard(PointCard pointCard) {
        pointCard.setCustomer(this);
        this.pointCard.add(pointCard);
    }
}
