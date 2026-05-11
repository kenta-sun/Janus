package io.github.kentasun.janus.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Janus 日志格式化工具类
 */
public class JanusLogUtils {

    private static final Logger log = LoggerFactory.getLogger(JanusLogUtils.class);

    public static final String SUCCESS_ICON = "✅";
    public static final String FAIL_ICON = "❌";

    public static String toJsonString(Object object) {
        try {
            if (object instanceof String) {
                return String.valueOf(object);
            } else {
                return JanusJsonUtils.writeValueAsString(object);
            }
        } catch (Throwable e) {
            log.error("[Janus] {} >> 对象序列化JSON字符串报错", FAIL_ICON, e);
        }
        return null;
    }
}