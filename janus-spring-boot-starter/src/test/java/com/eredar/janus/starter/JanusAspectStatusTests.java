package com.eredar.janus.starter;

import com.eredar.janus.core.constants.JanusConstants;
import com.eredar.janus.starter.dto.TestMessageResponse;
import com.eredar.janus.starter.dto.TestRequest;
import com.eredar.janus.starter.service.TransactionalService;
import com.eredar.janus.starter.utils.TestUtils;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

/**
 * 将切面状态传递出AOP的功能的相关测试
 */
@SpringBootTest(classes = JanusTestApplication.class)
public class JanusAspectStatusTests {

    @Autowired
    private TransactionalService transactionalService;

    static Stream<Arguments> testJanusAspectStatusDataProvider() {
        return Stream.of(
                Arguments.of(
                        TestRequest.builder().key(JanusConstants.PRIMARY).build(),
                        TestMessageResponse.builder().flag("0").message(JanusConstants.PRIMARY + "_" + JanusConstants.SECONDARY).build()
                ),
                Arguments.of(
                        TestRequest.builder().key(JanusConstants.SECONDARY).build(),
                        TestMessageResponse.builder().flag("0").message(JanusConstants.SECONDARY + "_" + JanusConstants.NULL).build()
                )
        );
    }

    @ParameterizedTest(name = "案例 {index}")
    @MethodSource("testJanusAspectStatusDataProvider")
    @Execution(ExecutionMode.CONCURRENT)
    public void testJanusAspectStatus(TestRequest request, TestMessageResponse expected) {
        TestMessageResponse actual = transactionalService.testJanusAspectStatus(request);

        /* 校验结果 */
        TestUtils.assertEquals(actual, expected);
    }
}
