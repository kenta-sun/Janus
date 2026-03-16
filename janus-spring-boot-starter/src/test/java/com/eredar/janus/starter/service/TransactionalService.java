package com.eredar.janus.starter.service;

import com.eredar.janus.starter.dao.TestRollbackMapper;
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
}
