package com.chengdu.jiq.common.dynamicdatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by jiyiqin on 2017/11/7.
 */
public class PlatformDynamicDatasource extends AbstractRoutingDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformDynamicDatasource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        LOGGER.info("切换到{}库", LookupKeyHolder.getLookupKey());
        return LookupKeyHolder.getLookupKey();
    }
}
