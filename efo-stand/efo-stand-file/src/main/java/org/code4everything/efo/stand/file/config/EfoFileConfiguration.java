package org.code4everything.efo.stand.file.config;

import org.code4everything.efo.stand.dao.repository.FileRepository;
import org.code4everything.efo.stand.file.service.FileService;
import org.code4everything.efo.stand.file.service.impl.FileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pantao
 * @since 2019-04-17
 */
@Configuration
public class EfoFileConfiguration {

    /**
     * 对外应该只提供一个接口
     */
    @Bean
    public FileService fileService(FileRepository fileRepository) {
        return new FileServiceImpl(fileRepository);
    }
}
