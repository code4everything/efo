package org.code4everything.efo.stand.web.service.impl;

import cn.hutool.core.lang.Console;
import org.code4everything.boot.annotation.AopLog;
import org.code4everything.efo.stand.web.service.LogTestService;
import org.springframework.stereotype.Service;

/**
 * @author pantao
 * @since 2019-04-16
 */
@Service
public class LogTestServiceImpl implements LogTestService {

    @Override
    @AopLog("日志测试")
    public void justLog(String msg) {
        Console.log(msg);
    }
}
