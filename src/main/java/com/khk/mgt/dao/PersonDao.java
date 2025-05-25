package com.khk.mgt.dao;

import com.khk.mgt.ds.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDao extends JpaRepository<Person, Long> {
    boolean existsByPhone(String phone);
}
