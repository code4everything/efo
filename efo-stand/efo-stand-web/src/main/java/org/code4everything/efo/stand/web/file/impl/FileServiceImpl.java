package org.code4everything.efo.stand.web.file.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.code4everything.boot.base.DateUtils;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.config.BootConfig;
import org.code4everything.boot.web.mvc.exception.ExceptionFactory;
import org.code4everything.efo.base.config.EfoConfig;
import org.code4everything.efo.base.constant.EfoError;
import org.code4everything.efo.base.model.vo.file.FileInfoVO;
import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.dao.domain.UserDO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.code4everything.efo.stand.web.file.FileService;
import org.code4everything.efo.stand.web.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@Service
public class FileServiceImpl implements FileService {

    /**
     * 日期路径
     */
    private static String datePath;

    /**
     * 支持的文件服务，默认为选择第一个文件服务
     */
    private final List<BaseFileService> services = new ArrayList<>();

    @Autowired
    public FileServiceImpl(FileRepository repository) {
        // 本地文件服务
        services.add(new LocalFileServiceImpl(repository));
        // 七牛云对象存储服务
        services.add(new QiniuFileServiceImpl(repository));
    }

    /**
     * 由定时任务调用，每日凌晨更新
     */
    public static void updateDatePath() {
        // 拼接日期路径：>year>month>day>
        Date date = DateUtils.getStartOfToday();
        StringJoiner pathJoiner = new StringJoiner(File.separator, File.separator, File.separator);
        pathJoiner.add(String.valueOf(DateUtil.year(date)));
        pathJoiner.add(StrUtil.padPre(String.valueOf(DateUtil.month(date) + 1), 2, "0"));
        pathJoiner.add(StrUtil.padPre(String.valueOf(DateUtil.dayOfMonth(date)), 2, "0"));
        datePath = pathJoiner.toString();
    }

    @Override
    public FileInfoVO upload(MultipartFile file, FileUploadVO uploadVO) {
        // 检测文件大小
        if (file.getSize() > BootConfig.getMaxUploadFileSize()) {
            final String maxSize = FileUtils.formatSize(BootConfig.getMaxUploadFileSize(), 2);
            EfoError error = EfoError.FILE_SIZE_EXCEED;
            throw ExceptionFactory.exception(error.getCode(), error.getStatus(), error.getMsg(maxSize));
        }
        UserDO user = ShiroUtils.getUser();
        uploadVO.setUserId(user.getId());
        uploadVO.setUsername(user.getUsername());
        // 路径格式：storage-root>username>year>month>day>
        String storagePath = EfoConfig.getInstance().getStorageRoot() + user.getUsername() + datePath;
        return getService().upload(storagePath, file, uploadVO).copyInto(new FileInfoVO());
    }

    private BaseFileService getService() {
        int locate = EfoConfig.getInstance().getStorageMode();
        if (locate == 0) {
            // 随机选一个服务，注：下标从1开始
            locate = RandomUtil.randomInt(services.size()) + 1;
        } else if (locate < 0 || locate > services.size()) {
            // 对于超出范围的设置，选择默认的文件服务
            locate = 1;
        }
        return services.get(locate - 1);
    }
}
