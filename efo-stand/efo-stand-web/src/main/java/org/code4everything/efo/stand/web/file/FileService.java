package org.code4everything.efo.stand.web.file;

import org.code4everything.efo.base.model.vo.file.FileInfoVO;
import org.code4everything.efo.base.model.vo.file.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author pantao
 * @since 2019/5/22
 **/
public interface FileService {

    FileInfoVO upload(MultipartFile file, FileUploadVO uploadVO);
}
