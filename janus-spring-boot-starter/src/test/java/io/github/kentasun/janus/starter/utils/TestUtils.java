package io.github.kentasun.janus.starter.utils;

import io.github.kentasun.janus.core.utils.JanusJsonUtils;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class TestUtils {

    public static void assertEquals(Object actual, Object expected) {
        Map<String, String> compareResMap = JanusJsonUtils.compare(actual, expected);
        if (!compareResMap.isEmpty()) {
            Assertions.fail(JanusJsonUtils.writeValueAsString(compareResMap));
        }
    }
}
