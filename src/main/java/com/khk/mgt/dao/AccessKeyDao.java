package com.khk.mgt.dao;

import com.khk.mgt.ds.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccessKeyDao extends JpaRepository<AccessKey, Long> {
    Collection<AccessKey> findByActiveTrue();
}
