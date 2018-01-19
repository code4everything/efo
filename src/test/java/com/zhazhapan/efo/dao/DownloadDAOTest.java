package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.EfoApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @date 2018/1/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadDAOTest {

    static {
        EfoApplicationTest.setSettings();
    }

    @Autowired
    DownloadDAO downloadDAO;

    @Test
    public void testGetDownloadBy() {
        System.out.println(downloadDAO.getDownloadBy(1, 1, 0));
    }
}
