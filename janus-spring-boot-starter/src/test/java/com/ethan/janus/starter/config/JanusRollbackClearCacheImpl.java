package com.ethan.janus.starter.config;

import com.ethan.janus.core.rollback.JanusRollbackClearCache;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JanusRollbackClearCacheImpl implements JanusRollbackClearCache {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void clearCache() {
        sqlSessionTemplate.clearCache();
    }
}
