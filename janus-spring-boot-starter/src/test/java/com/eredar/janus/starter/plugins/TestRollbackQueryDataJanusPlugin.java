package com.eredar.janus.starter.plugins;

import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.plugin.JanusPlugin;
import com.eredar.janus.starter.dao.TestRollbackMapper;
import com.eredar.janus.starter.dto.TestRequest;
import com.eredar.janus.starter.dto.TestRollbackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestRollbackQueryDataJanusPlugin implements JanusPlugin {

    @Autowired
    private TestRollbackMapper testRollbackMapper;

    @Override
    public int getOrder() {
        return 10000;
    }

    @Override
    public void afterPrimaryExecute(JanusContext context) {
        List<TestRollbackEntity> queryRes = getQueryRes(context);
        context.setPrimaryQueryRes(queryRes);
    }

    @Override
    public void afterSecondaryExecute(JanusContext context) {
        List<TestRollbackEntity> queryRes = getQueryRes(context);
        context.setSecondaryQueryRes(queryRes);
    }

    private List<TestRollbackEntity> getQueryRes(JanusContext context) {
        // 获取入参中的key
        Object[] args = context.getArgs();
        TestRequest request = (TestRequest) args[0];
        String reqKey = request.getKey();
        // 公共key
        List<String> keyList = new ArrayList<>(Arrays.asList("exist", "delete"));
        keyList.add(reqKey);
        // 查询落表结果
        List<TestRollbackEntity> queryRes = new ArrayList<>();
        for (String key : keyList) {
            List<TestRollbackEntity> queryList = testRollbackMapper.selectByKey(key);
            queryRes.addAll(queryList);
        }
        // 用于触发一级缓存
        testRollbackMapper.selectNumByKey("exist");
        // 排序并返回
        return queryRes.stream()
                .sorted(Comparator.comparing(TestRollbackEntity::getTblKey)
                        .thenComparing(TestRollbackEntity::getTblNum))
                .collect(Collectors.toList());
    }
}
