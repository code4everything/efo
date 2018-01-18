package com.zhazhapan.efo.dao;

import com.zhazhapan.efo.entity.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pantao
 * @date 2018/1/18
 */
@Repository
public interface CategoryDAO {

    /**
     * 添加一个分类
     *
     * @param name 分类名
     */
    @Insert("insert into category(name) values(#{name})")
    void insertCategory(String name);

    /**
     * 通过编号删除一个分类
     *
     * @param id 编号
     */
    @Delete("delete from category where id=#{id}")
    void removeCategoryById(int id);

    /**
     * 通过名称删除一个分类
     *
     * @param name 分类名称
     */
    @Delete("delete from category where name=#{name}")
    void removeCategoryByName(String name);

    /**
     * 更新一个分类名
     *
     * @param name 分类名
     */
    @Update("update category set name=#{name} where id=#{id}")
    void updateNameById(@Param("id") int id, @Param("name") String name);

    /**
     * 通过分类名更新分类名
     *
     * @param newName 新的分类名
     * @param oldName 旧的分类名
     */
    @Update("update category set name=#{newName} where name=#{oldName}")
    void updateNameByName(String newName, String oldName);

    /**
     * 获取所有分类
     *
     * @return {@link List}
     */
    @Select("select * from category")
    List<Category> getAllCategory();

    /**
     * 通过编号获取一个分类
     *
     * @param id 编号
     *
     * @return {@link Category}
     */
    @Select("select * from category where id=#{id}")
    Category getCategoryById(int id);

    /**
     * 通过名称获取一个分类
     *
     * @param name 名称
     *
     * @return {@link Category}
     */
    @Select("select * from category where name=#{name}")
    Category getCategoryByName(String name);
}
