package com.zhazhapan.efo.entity;

import com.zhazhapan.efo.util.BeanUtils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 分类表
 *
 * @author pantao
 * @date 2018/1/11
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 分类名称
     */
    @Column
    private String name;

    @Column(name = "create_time")
    private Timestamp createTime;

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name, Timestamp createTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return BeanUtils.toPrettyJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
