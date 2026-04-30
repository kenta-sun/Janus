package com.eredar.janus.starter.service;

import com.eredar.janus.starter.dto.TestRequest;
import com.eredar.janus.starter.dto.TestResponse;

public interface TestInterface {

    TestResponse testAsyncCompare1(TestRequest request);

    TestResponse testAsyncCompare2(TestRequest request);

    TestResponse testSyncCompare(TestRequest request);

    TestResponse testRollbackOne(TestRequest request);

    TestResponse testRollbackAll(TestRequest request);

    TestResponse testIgnore();

    void testCompareThrottling(TestRequest request);

    void testCompareThrottling2(TestRequest request);

    void testCompareThrottling3(TestRequest request);

    void janusAspectStatus1(TestRequest request);

    void janusAspectStatus2(TestRequest request);
}
