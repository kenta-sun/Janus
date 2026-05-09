package io.github.kentasun.janus.starter;

import io.github.kentasun.janus.core.utils.JanusJsonUtils;
import io.github.kentasun.janus.starter.dto.PluginRes;
import io.github.kentasun.janus.starter.dto.TestRequest;
import io.github.kentasun.janus.starter.service.TestInterface;
import io.github.kentasun.janus.starter.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

/**
 * 异步、流量平衡功能 测试
 */
@SpringBootTest(classes = JanusTestApplication.class)
public class JanusThrottlingTests {

    @Autowired
    private TestInterface testInterface;

    public static PluginRes pluginRes;
    public static LongAdder longAdder = new LongAdder();

    static Stream<Arguments> testAsyncCompareDataProvider() {
        return Stream.of(
                Arguments.of(
                        TestRequest.builder().key("primary").build(),
                        "{\"methodId\":\"testAsyncCompare2\",\"businessKey\":\"primary\",\"masterBranchName\":\"primary\",\"compareRes\":{\"compareStatus\":\"success\"}}"
                )
        );
    }

    @ParameterizedTest(name = "案例 {index}: requestStr={0}")
    @MethodSource("testAsyncCompareDataProvider")
    public void testAsyncCompare(TestRequest request, String pluginResExpectedStr) {
        /* 整理测试数据 */
        PluginRes expected = JanusJsonUtils.readValue(pluginResExpectedStr, new TypeReference<PluginRes>() {
        });

        /* 执行测试方法 */
        for (int i = 0; i < 20; i++) {
            testInterface.testAsyncCompare1(request);
        }

        pluginRes = new PluginRes();
        testInterface.testAsyncCompare2(request);

        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.err.println(JanusJsonUtils.writeValueAsString(pluginRes));
        System.err.println("1方法比对次数：" + longAdder.longValue());
        Assertions.assertTrue(longAdder.longValue() < 15);

        /* 校验结果 */
        TestUtils.assertEquals(pluginRes, expected);
    }
}
