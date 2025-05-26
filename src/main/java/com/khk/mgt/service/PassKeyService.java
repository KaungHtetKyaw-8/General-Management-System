package com.khk.mgt.service;

import com.khk.mgt.dao.AccessKeyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PassKeyService {
    @Autowired
    private AccessKeyDao accessKeyDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean isValid(String rawKey) {
        return accessKeyDao.findByActiveTrue()
                .stream()
                .anyMatch(stored -> encoder.matches(rawKey, stored.getHashedKey()));
    }
}
