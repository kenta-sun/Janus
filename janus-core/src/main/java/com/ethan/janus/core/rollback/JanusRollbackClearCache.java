package com.ethan.janus.core.rollback;

/**
 * `connection.rollback(savepoint);`之后，清理持久层框架的缓存
 * <p>以 MyBatis 的清理一级缓存做示例：  sqlSessionTemplate.clearCache();
 */
public interface JanusRollbackClearCache {

    void clearCache();
}
