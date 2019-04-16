package org.code4everything.efo.stand.web.test;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.efo.stand.web.service.LogTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author pantao
 * @since 2019-04-16
 */
@Component
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class LogTest {

    @Autowired
    private LogTestService logTestService;

    @Test
    public void testLog() {
        logTestService.justLog(RandomUtil.randomString(12));
    }
}
