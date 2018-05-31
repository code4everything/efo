package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.EfoApplicationTest;
import com.zhazhapan.modules.constant.ValueConsts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @since 2018/1/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadedDAOTest {

    static {
        EfoApplicationTest.setSettings();
    }

    @Autowired
    DownloadedDAO downloadDAO;

    @Test
    public void testGetDownloadBy() {
        System.out.println(downloadDAO.listDownloadedBy(1, 1, "", ValueConsts.ZERO_INT, 0));
    }
}
