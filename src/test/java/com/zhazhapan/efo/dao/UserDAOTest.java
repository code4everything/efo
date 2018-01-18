package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.entity.User;
import com.zhazhapan.util.Formatter;
import com.zhazhapan.util.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @date 2018/1/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testInsertUser() {
        String username = RandomUtils.getRandomStringOnlyLowerCase(6);
        String realName = RandomUtils.getRandomStringOnlyLowerCase(6);
        String email = RandomUtils.getRandomEmail();
        String password = RandomUtils.getRandomStringWithoutSymbol(16);
        User user = new User(username, realName, email, password);
        userDAO.insertUser(user);
    }

    @Test
    public void testGetAllUser() {
        System.out.println(Formatter.listToJson(userDAO.getAllUser()));
    }

    @Test
    public void testGetUser() {
        User user = userDAO.getUserById(1);
        System.out.println(user.toString());
    }
}
