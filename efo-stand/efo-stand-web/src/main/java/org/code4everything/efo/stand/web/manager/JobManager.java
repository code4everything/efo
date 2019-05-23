package org.code4everything.efo.stand.web.manager;

import org.code4everything.efo.stand.web.file.impl.FileServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author pantao
 * @since 2019/5/23
 **/
@Component
public class JobManager {

    @Scheduled(cron = "0 0 0 * * *")
    public void freeTask() {
        FileServiceImpl.updateDatePath();
        // TODO: 2019/5/23 删除标记了需要删除的文件
    }
}
