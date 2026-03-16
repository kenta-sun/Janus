package com.eredar.janus.starter.plugins;

import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.plugin.JanusPlugin;
import com.eredar.janus.starter.JanusThrottlingTests;
import org.springframework.stereotype.Component;

@Component
public class AsyncResJanusPlugin implements JanusPlugin {

    @Override
    public void afterCompare(JanusContext context) {
        JanusThrottlingTests.pluginRes.businessKey = context.getBusinessKey();
        JanusThrottlingTests.pluginRes.masterBranchName = context.getMasterBranchName();
        JanusThrottlingTests.pluginRes.compareRes = context.getCompareRes();
        JanusThrottlingTests.pluginRes.methodId = context.getMethodId();
    }
}
