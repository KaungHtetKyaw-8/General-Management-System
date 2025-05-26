package com.khk.mgt.dao;

import com.khk.mgt.ds.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogDao extends JpaRepository<AccessLog, Long> {

}
