package io.github.kentasun.janus.starter;

import io.github.kentasun.janus.starter.dto.TestRequest;
import io.github.kentasun.janus.starter.service.TestInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 异步、流量平衡功能 测试
 */
@SpringBootTest(classes = JanusTestApplication.class)
public class CompareThrottlingTests {

    @Autowired
    private TestInterface testInterface;

    public static LongAdder longAdder = new LongAdder();
    public static LongAdder longAdder2 = new LongAdder();

    @Test
    public void testCompareThrottling() {
        for (int i = 0; i < 20; i++) {
            testInterface.testCompareThrottling(TestRequest.builder().key(String.valueOf(i + 1)).build());
        }

        Assertions.assertEquals(3, longAdder.longValue());
    }

    @Test
    public void testCompareThrottling2() {
        for (int i = 0; i < 12; i++) {
            testInterface.testCompareThrottling3(TestRequest.builder().key(String.valueOf(i + 1)).build());
        }
        for (int i = 0; i < 12; i++) {
            testInterface.testCompareThrottling2(TestRequest.builder().key(String.valueOf(i + 1)).build());
        }
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, longAdder2.longValue());
    }
}
