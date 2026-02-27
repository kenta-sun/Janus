package com.ethan.janus.starter.service;

import com.ethan.janus.core.annotation.Janus;
import com.ethan.janus.core.constants.CompareType;
import com.ethan.janus.starter.annotation.TestAnnotation;
import com.ethan.janus.starter.dto.TestRequest;
import com.ethan.janus.starter.dto.TestResponse;
import com.ethan.janus.starter.plugins.ExecuteTimeJanusPlugin;
import com.ethan.janus.starter.plugins.TestAnnotationJanusPlugin;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PrimaryService implements TestInterface {

    @Janus(
            methodId = "testSyncCompare",
            compareType = CompareType.SYNC_COMPARE,
            isAsyncCompare = false,
            businessKey = "buildKey(#request.key, 'qqq')",
            plugins = {TestAnnotationJanusPlugin.class, ExecuteTimeJanusPlugin.class}
    )
    @TestAnnotation(value = "Archimonde")
    @Override
    public TestResponse testSyncCompare(TestRequest request) {
        if ("1".equals(request.getKey())) {
            return new TestResponse(1);
        } else if ("2".equals(request.getKey())) {
            return new TestResponse(2);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new TestResponse(0);
    }

//    @Janus(
//            methodId = "testRollbackOne",
//            compareType = CompareType.SYNC_ROLLBACK_ONE_COMPARE,
//            isAsyncCompare = false,
//            businessKey = "#request.key",
//            plugins = {TestAnnotationJanusPlugin.class, ExecuteTimeJanusPlugin.class}
//    )
    @Override
    public TestResponse testRollbackOne(TestRequest request) {
        return new TestResponse(0);
    }
}
