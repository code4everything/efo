package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.EfoApplicationTest;
import com.zhazhapan.util.Formatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @since 2018/2/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileDAOTest {

    @Autowired
    FileDAO fileDAO;

    @Test
    public void testRemoveFile() {
        assert fileDAO.removeById(3);
    }

    @Test
    public void testGetUserDownloaded() {
        EfoApplicationTest.setSettings();
        System.out.println(Formatter.listToJson(fileDAO.listUserDownloaded(2, 0, "")));
    }
}
