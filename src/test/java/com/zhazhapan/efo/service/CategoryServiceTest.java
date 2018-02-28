package com.zhazhapan.efo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pantao
 * @since 2018/2/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    ICategoryService categoryService;

    @Test
    public void testGetIdByName() {
        System.out.println(categoryService.getIdByName("fff"));
    }
}
