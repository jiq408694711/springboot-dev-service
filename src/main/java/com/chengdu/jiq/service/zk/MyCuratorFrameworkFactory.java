package com.chengdu.jiq.service.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCuratorFrameworkFactory {
    private static final Logger logger = LoggerFactory.getLogger(MyCuratorFrameworkFactory.class);
    private String zookeeperServer;
    private CuratorFramework client;

    public MyCuratorFrameworkFactory(String zookeeperServer) {
        this.zookeeperServer = zookeeperServer;
    }

    public void init() {
        this.client = CuratorFrameworkFactory.builder().connectString(this.zookeeperServer)
                .sessionTimeoutMs(1000)    // 连接超时时间
                .connectionTimeoutMs(1000) // 会话超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) // 刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
                .build();
        this.client.start();
    }

    public void destroy() {
        try {
            if (this.client != null) {
                CloseableUtils.closeQuietly(this.client);//建议放在finally块中
            }
        } catch (Exception e) {
            logger.error("stop zookeeper client error {}", e.getMessage());
        }
    }

    public CuratorFramework getClient() {
        return client;
    }
}