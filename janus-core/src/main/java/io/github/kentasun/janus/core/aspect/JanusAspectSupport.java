package io.github.kentasun.janus.core.aspect;

import io.github.kentasun.janus.core.constants.JanusConstants;
import io.github.kentasun.janus.core.dto.JanusAspectStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供用户在切面外获取切面信息的静态方法。
 */
public class JanusAspectSupport {

    private final static ThreadLocal<Boolean> isFirstThreadLocal = ThreadLocal.withInitial(() -> Boolean.TRUE);

    private final static ThreadLocal<Map<String, JanusAspectStatus>> statusThreadLocal = new ThreadLocal<>();

    /**
     * 当前线程是否第一次进入 JanusAspect
     * @return true-是第一次进入；false-不是第一次进入
     */
    public static boolean isFirst() {
        return isFirstThreadLocal.get();
    }

    /**
     * 获取主分支名
     *
     * @param methodId 方法id
     * @return 主分支名。若因为 JanusAspect 功能未开启导致没设置主分支名，默认返回 primary
     */
    public static String getMasterBranchName(String methodId) {
        JanusAspectStatus status = getStatus(methodId);
        if (status == null) {
            // JanusAspect 功能未开启，或者无法获取分流结果。直接返回 null。
            return JanusConstants.NULL;
        } else {
            return status.getMasterBranchName();
        }
    }

    /**
     * 当前线程第一次进入 JanusAspect，初始化 statusThreadLocal，isFirst 设置为 false
     *
     * @return true-是第一次进入，成功初始化；false-不是第一次进入，不做任何操作
     */
    protected static boolean init() {
        if (isFirst()) {
            statusThreadLocal.remove();
            statusThreadLocal.set(new ConcurrentHashMap<>());
            isFirstThreadLocal.set(Boolean.FALSE);
            return true;
        } else {
            return false;
        }
    }

    protected static void putStatus(String methodId, JanusAspectStatus status) {
        statusThreadLocal.get().put(methodId, status);
    }

    public static JanusAspectStatus getStatus(String methodId) {
        return statusThreadLocal.get().get(methodId);
    }

    /**
     * isFirst 重置为 true
     */
    protected static void resetIsFirst() {
        isFirstThreadLocal.set(Boolean.TRUE);
    }
}
