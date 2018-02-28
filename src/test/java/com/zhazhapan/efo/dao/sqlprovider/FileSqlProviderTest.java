package com.zhazhapan.efo.dao.sqlprovider;

import com.zhazhapan.efo.EfoApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @since 2018/2/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileSqlProviderTest {

    @Autowired
    FileSqlProvider fileSqlProvider;

    @Test
    public void updateAuthById() {
        System.out.println(fileSqlProvider.updateAuthById());
    }

    @Test
    public void getAll() {
        EfoApplicationTest.setSettings();
        System.out.println(fileSqlProvider.getAll(0, 0, "", ""));
    }

    @Test
    public void getUserUploaded() {
        System.out.println(fileSqlProvider.getUserUploaded(0, ""));
    }

    @Test
    public void getUserDownloaded() {
        System.out.println(fileSqlProvider.getUserDownloaded(0, ""));
    }
}
