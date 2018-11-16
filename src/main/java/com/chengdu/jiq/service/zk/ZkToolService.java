package com.chengdu.jiq.service.zk;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.io.FileWriter;
import java.util.List;

@Service
public class ZkToolService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkToolService.class);
    @Inject
    private MyCuratorFrameworkFactory curatorFrameworkFactory;

    public void parse() throws Exception {
        CuratorFramework curatorFramework = curatorFrameworkFactory.getClient();
        FileWriter fileWritter = new FileWriter("D:\\zk.txt", true);   //true = append file
        parseRecursion(curatorFramework, "/", fileWritter);
        fileWritter.flush();
        fileWritter.close();
    }

    public void parseRecursion(CuratorFramework curatorFramework, String path, FileWriter fileWritter) throws Exception {
        byte[] value = curatorFramework.getData().forPath(path);
        if (null != value && value.length > 0) {
            LOGGER.warn("create " + path + " " + new String(value));
            fileWritter.write("create " + path + " " + new String(value) + "\n");
        }
        List<String> list = curatorFramework.getChildren().forPath(path);
        if (!CollectionUtils.isEmpty(list)) {
            for (String p : list) {
                parseRecursion(curatorFramework, "/".equals(path) ? path + p : path + "/" + p, fileWritter);
            }
        }
    }
}
