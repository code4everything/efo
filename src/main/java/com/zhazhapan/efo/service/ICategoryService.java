package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.Category;

import java.util.List;

/**
 * @author pantao
 * @since 2018/1/30
 */
public interface ICategoryService {

    /**
     * 添加一个分类
     *
     * @param name 分类名称
     *
     * @return 是否添加成功
     */
    boolean insert(String name);

    /**
     * 删除一个分类
     *
     * @param id 分类编号
     *
     * @return 是否删除成功
     */
    boolean remove(int id);

    /**
     * 更新分类
     *
     * @param id 分类编号
     * @param name 分类名称
     *
     * @return 是否更新成功
     */
    boolean update(int id, String name);

    /**
     * 获取一个分类
     *
     * @param id 分类编号
     *
     * @return {@link Category}
     */
    Category getById(int id);

    /**
     * 获取所有的分类
     *
     * @return {@link List}
     */
    List<Category> getAll();

    /**
     * 通过分类名获取ID
     *
     * @param name 分类名
     *
     * @return {@link Integer}
     */
    int getIdByName(String name);
}
