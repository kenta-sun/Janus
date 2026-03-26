package com.eredar.janus.starter.service;

import com.eredar.janus.core.aspect.JanusAspectSupport;
import com.eredar.janus.starter.dao.TestRollbackMapper;
import com.eredar.janus.starter.dto.TestMessageResponse;
import com.eredar.janus.starter.dto.TestRequest;
import com.eredar.janus.starter.dto.TestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalService {

    @Autowired
    private TestInterface testInterface;
    @Autowired
    private TestRollbackMapper testRollbackMapper;

    @Transactional(rollbackFor = Throwable.class)
    public TestResponse testRollbackOne(TestRequest request) {
        Integer existNum = testRollbackMapper.selectNumByKey("exist");
        testRollbackMapper.updateByKey("exist", existNum + 1);
        return testInterface.testRollbackOne(request);
    }

    @Transactional(rollbackFor = Throwable.class)
    public TestResponse testRollbackAll(TestRequest request) {
        Integer existNum = testRollbackMapper.selectNumByKey("exist");
        testRollbackMapper.updateByKey("exist", existNum + 1);
        return testInterface.testRollbackAll(request);
    }

    /**
     * 获取 janusAspectStatus 方法 masterBranchName，写入 message
     */
    public TestMessageResponse testJanusAspectStatus(TestRequest request) {
        this.testInterface.janusAspectStatus1(request);
        String masterBranchName1 = JanusAspectSupport.getMasterBranchName("janusAspectStatus1");
        String masterBranchName2 = JanusAspectSupport.getMasterBranchName("janusAspectStatus2");
        return TestMessageResponse.builder()
                .flag("0")
                .message(masterBranchName1 + "_" + masterBranchName2)
                .build();
    }
}
