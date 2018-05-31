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
 * @since 2018/1/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthDAOTest {

    static {
        EfoApplicationTest.setSettings();
    }

    @Autowired
    private AuthDAO authDAO;

    @Test
    public void testGetAuthBy() {
        System.out.println(Formatter.listToJson(authDAO.listAuthBy(0, 0, 0, "", 0)));
    }
}
