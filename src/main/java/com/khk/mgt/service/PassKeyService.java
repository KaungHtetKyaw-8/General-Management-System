package com.khk.mgt.service;

import com.khk.mgt.dao.AccessKeyDao;
import com.khk.mgt.ds.AccessKey;
import com.khk.mgt.dto.common.AccessKeyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PassKeyService {
    @Autowired
    private AccessKeyDao accessKeyDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public AccessKey findByHashPassKey(String rawKey) {
        return accessKeyDao.findByActiveTrue()
                .stream()
                .filter(stored -> encoder.matches(rawKey,stored.getHashedKey()))
                .findFirst()
                .orElse(null);
    }

    public AccessKey findById(Long id) {
        return accessKeyDao.findById(id).orElse(null);
    }

    public boolean isValid(String rawKey) {
        return accessKeyDao.findByActiveTrue()
                .stream()
                .anyMatch(stored -> encoder.matches(rawKey, stored.getHashedKey()));
    }

    public void saveAccessKey(AccessKeyDto accessKeyDto) {
        AccessKey accessKey = new AccessKey();
        accessKey.setHashedKey(encoder.encode(accessKeyDto.getSecretKey()));
        accessKey.setUserName(accessKeyDto.getUserName());
        accessKey.setActive(true);
        accessKeyDao.save(accessKey);
    }
}
