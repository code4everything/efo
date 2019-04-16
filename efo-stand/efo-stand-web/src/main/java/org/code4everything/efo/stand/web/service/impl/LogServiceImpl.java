package org.code4everything.efo.stand.web.service.impl;

import org.code4everything.boot.bean.LogBean;
import org.code4everything.efo.stand.dao.domain.Log;
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
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public Log saveException(Log log, Throwable throwable) {
        log.setExceptionClass(throwable.getClass().getName());
        log.setExceptionDetail(throwable.getMessage());
        return save(log);
    }

    @Override
    public Log getLog(LogBean logBean) {
        Log log = logBean.copyInto(new Log());
        log.setCreateTime(LocalDateTime.now());
        return log;
    }
}
