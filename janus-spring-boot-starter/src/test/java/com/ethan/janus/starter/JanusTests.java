package com.ethan.janus.starter;

import com.ethan.janus.core.utils.JanusJsonUtils;
import com.ethan.janus.starter.dto.PluginRes;
import com.ethan.janus.starter.dto.TestRequest;
import com.ethan.janus.starter.dto.TestResponse;
import com.ethan.janus.starter.service.TestInterface;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.*;
import java.util.stream.Stream;

/**
 * Janus 框架功能测试
 */
@SpringBootTest(classes = {
        JanusAutoConfiguration.class,
        JanusTests.TestConfig.class
})
public class JanusTests {

    @Autowired
    private TestInterface testInterface;

    public static PluginRes pluginRes;

    @TestConfiguration
    @EnableAspectJAutoProxy(proxyTargetClass = true)
    // 说明：只扫描测试包下的 Service，避免扫描范围过大
    static class TestConfig {
        // 这里无需写 Bean 方法，保持空即可
    }

    static Stream<Arguments> janusTestDataProvider() {
        return Stream.of(
                Arguments.of(
                        TestRequest.builder().key("1").build(),
                        TestResponse.builder().number(1).build(),
                        "{\"methodId\":\"testSyncCompare\",\"compareRes\":{\"compareStatus\":\"success\"},\"businessKey\":\"1_qqq\",\"testAnnotationKey\":\"Archimonde\"}"
                ),
                Arguments.of(
                        TestRequest.builder().key("2").build(),
                        TestResponse.builder().number(2).build(),
                        "{\"methodId\":\"testSyncCompare\",\"compareRes\":{\"compareStatus\":\"different\",\"diffFieldMap\":{\"res.number\":\"2 / 3\"}},\"businessKey\":\"2_qqq\",\"testAnnotationKey\":\"Archimonde\"}"
                )
        );
    }

    @ParameterizedTest(name = "案例 {index}: requestStr={0}")
    @MethodSource("janusTestDataProvider")
    public void janusTest(TestRequest request, TestResponse responseExpected, String pluginResExpectedStr) {
        /* 整理测试数据 */
        PluginRes pluginResExpected = JanusJsonUtils.readValue(pluginResExpectedStr, new TypeReference<PluginRes>() {
        });
        Map<String, Object> expected = new HashMap<>();
        expected.put("response", responseExpected);
        expected.put("pluginRes", pluginResExpected);

        /* 执行测试方法 */
        pluginRes = new PluginRes();
        TestResponse response = testInterface.testSyncCompare(request);
        System.err.println(JanusJsonUtils.writeValueAsString(pluginRes));

        /* 校验结果 */
        Assertions.assertTrue(pluginRes.primaryTime > 0);
        pluginRes.primaryTime = null;
        Assertions.assertTrue(pluginRes.secondaryTime > 0);
        pluginRes.secondaryTime = null;
        Map<String, Object> actual = new HashMap<>();
        actual.put("response", response);
        actual.put("pluginRes", pluginRes);
        Map<String, String> compareResMap = JanusJsonUtils.compareObj(actual, expected);
        if (!compareResMap.isEmpty()) {
            Assertions.fail(JanusJsonUtils.writeValueAsString(compareResMap));
        }
    }
}
