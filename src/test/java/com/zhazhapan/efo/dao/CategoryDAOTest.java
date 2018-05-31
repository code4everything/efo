package com.zhazhapan.efo.dao;

import com.zhazhapan.util.Formatter;
import com.zhazhapan.util.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @since 2018/1/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryDAOTest {

    @Autowired
    CategoryDAO categoryDAO;

    @Test
    public void testInsertCategory() {
        for (int i = 0; i < 10; i++) {
            categoryDAO.insertCategory(RandomUtils.getRandomStringOnlyLowerCase(6));
        }
    }

    @Test
    public void testRemoveCategoryById() {
        categoryDAO.removeCategoryById(1);
    }

    @Test
    public void testUpdateName() {
        categoryDAO.updateNameById(3, "update");
    }

    @Test
    public void testGetAllCategory() {
        System.out.println(Formatter.listToJson(categoryDAO.listCategory()));
    }

    @Test
    public void testGetCategoryById() {
        System.out.println(categoryDAO.getCategoryById(6).toString());
    }
}
