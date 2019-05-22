package org.code4everything.efo.stand.web.file;

import org.code4everything.efo.base.model.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author pantao
 * @since 2019/5/22
 **/
public interface BaseFileService {

    FileInfoVO upload(MultipartFile file, Map<String, Object> params);
}
