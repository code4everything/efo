package org.code4everything.efo.stand.web.file.impl;

import cn.hutool.core.util.RandomUtil;
import org.code4everything.efo.base.config.EfoConfig;
import org.code4everything.efo.base.model.vo.file.FileInfoVO;
import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.web.file.BaseFileService;
import org.code4everything.efo.stand.web.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pantao
 * @since 2019/5/22
 **/
@Service
public class FileServiceImpl implements FileService {

    private final List<BaseFileService> services = new ArrayList<>();

    @Autowired
    public FileServiceImpl(FileRepository repository) {
        // 第一个文件服务将会是默认的
        services.add(new LocalFileServiceImpl(repository));
        services.add(new QiniuFileServiceImpl(repository));
    }

    @Override
    public FileInfoVO upload(MultipartFile file, FileUploadVO uploadVO) {
        return getService().upload(null, file, uploadVO);
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
