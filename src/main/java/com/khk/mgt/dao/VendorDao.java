package com.khk.mgt.dao;

import com.khk.mgt.ds.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorDao extends CrudRepository<Vendor, Integer> {
}
