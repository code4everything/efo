package org.code4everything.efo.base.config;

import cn.hutool.core.util.ObjectUtil;
import lombok.*;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.base.bean.BaseBean;

import java.io.Serializable;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EfoConfig implements BaseBean, Serializable {

    private static final long serialVersionUID = -572020698000897710L;

    private static final EfoConfig CONFIG = new EfoConfig();

    private static final String DEFAULT_STORAGE_ROOT = FileUtils.currentWorkDir();

    /**
     * 文件保存路径，绝对路径
     */
    private String storageRoot;

    /**
     * 存储模式：0随机，1本地，2七牛
     */
    @NonNull
    private Byte storageMode = 1;

    public static EfoConfig getInstance() {
        return CONFIG;
    }

    @Generated
    public String getStorageRoot() {
        return ObjectUtil.defaultIfNull(storageRoot, DEFAULT_STORAGE_ROOT);
    }
}
