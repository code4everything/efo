package com.zhazhapan.efo.entity;

import com.alibaba.fastjson.JSONObject;
import com.zhazhapan.util.Formatter;

import javax.persistence.*;
import java.util.Date;

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
    private Date createTime;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("createTime", createTime);
        return Formatter.formatJson(jsonObject.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
