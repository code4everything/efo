package org.code4everything.efo.stand.web.file;

import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.code4everything.efo.stand.dao.domain.FileDO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2019/5/22
 **/
public interface BaseFileService {

    FileDO upload(String storagePath, MultipartFile file, FileUploadVO uploadVO);
}
