package com.zhazhapan.efo.service;

import com.zhazhapan.efo.entity.Auth;
import com.zhazhapan.efo.model.AuthRecord;

/**
 * @author pantao
 * @date 2018/2/1
 */
public interface IAuthService {

    /**
     * 获取一个权限
     *
     * @param fileId 文件编号
     *
     * @return {@link AuthRecord}
     */
    AuthRecord getByFileId(long fileId);

    /**
     * 添加一个默认权限
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     *
     * @return 是否添加成功
     */
    boolean insertDefaultAuth(int userId, long fileId);

    /**
     * 添加一个权限
     *
     * @param auth {@link Auth}
     *
     * @return 是否添加成功
     */
    boolean insertAuth(Auth auth);

    /**
     * 通过文件编号删除权限
     *
     * @param fileId 文件编号
     *
     * @return 是否删除成功
     */
    boolean removeByFileId(long fileId);
}
