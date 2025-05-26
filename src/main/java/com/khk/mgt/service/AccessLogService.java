package com.khk.mgt.service;

import com.khk.mgt.dao.AccessLogDao;
import com.khk.mgt.ds.AccessKey;
import com.khk.mgt.ds.AccessLog;
import com.khk.mgt.dto.common.AccessLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccessLogService {
    @Autowired
    PassKeyService passKeyService;

    @Autowired
    AccessLogDao accessLogDao;

    public List<AccessLogDto> getAllLog(){
        return accessLogDao.findAll().stream()
                .map(log -> {
                    AccessLogDto dto = new AccessLogDto();

                    dto.setAccessUser(log.getAccessKey().getUserName());
                    dto.setIsActive(log.getAccessKey().isActive());
                    dto.setAccessedAt(log.getAccessedAt());
                    dto.setRemoteIp(log.getRemoteIp());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void saveAccessLogInfo(AccessLogDto accessLogDto) {
        AccessLog accessLog = new AccessLog();
        AccessKey accessKey = passKeyService.findById(accessLogDto.getAccessKeyId());
        if (accessKey == null) {
            return;
        }
        accessLog.setAccessKey(accessKey);
        accessLog.setAccessedAt(accessLogDto.getAccessedAt());
        accessLog.setRemoteIp(accessLogDto.getRemoteIp());

        accessLogDao.save(accessLog);
    }
}
