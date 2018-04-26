package com.chengdu.jiq.service.fileupload.service;

import com.chengdu.jiq.common.utils.FileUtils;
import com.chengdu.jiq.model.bo.exception.ParamException;
import com.chengdu.jiq.model.bo.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by jiyiqin on 2017/9/16.
 */
@Service
public class StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

    private List<String> filenames = new ArrayList();

    //    @PostConstruct
    public void init(String filePath) {
        if (!CollectionUtils.isEmpty(filenames)) {
            return;
        }
        filenames.add("test1.txt");
        filenames.add("test2.txt");
        filenames.add("test3.txt");
        filenames.stream().forEach(filename -> {
            try {
                String content = "hello spring boot";
                FileUtils.uploadFile(content.getBytes(), filePath, filename);
            } catch (Exception e) {
                LOGGER.error("create file error, because of : {}", e);
            }
        });
    }

    public void store(MultipartFile file, String filePath) throws Exception {
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
            throw new ServerException("上传文件不能为空");
        }

        try {
            filenames.add(file.getOriginalFilename());
            FileUtils.uploadFile(file.getBytes(), filePath, file.getOriginalFilename());
        } catch (Exception e) {
            throw new ParamException("存储文件失败");
        }
    }

    public Stream<Path> loadAll(String filePath) {
        return filenames.stream().map(filename -> new File(filePath + filename).toPath());
    }

    public Resource loadAsResource(String filePath, String filename) throws FileNotFoundException {
        FileSystemResource resource = new FileSystemResource(new File(filePath + filename));
        return resource;
    }

    @PreDestroy
    public void deleteAll() {
        return;
    }

}
