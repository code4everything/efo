package org.code4everything.efo.stand.web.service.impl;

import org.code4everything.boot.log.MethodLog;
import org.code4everything.efo.stand.dao.domain.LogDO;
import org.code4everything.efo.stand.dao.repository.LogRepository;
import org.code4everything.efo.stand.web.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author pantao
 * @since 2019-04-16
 */
@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {this.logRepository = logRepository;}

    @Override
    public LogDO save(LogDO log) {
        return logRepository.save(log);
    }

    @Override
    public LogDO saveException(LogDO log, Throwable throwable) {
        log.setExceptionClass(throwable.getClass().getName());
        log.setExceptionDetail(throwable.getMessage());
        return save(log);
    }

    @Override
    public LogDO getLog(MethodLog methodLog) {
        LogDO log = methodLog.copyInto(new LogDO());
        log.setCreateTime(LocalDateTime.now());
        return log;
    }
}
