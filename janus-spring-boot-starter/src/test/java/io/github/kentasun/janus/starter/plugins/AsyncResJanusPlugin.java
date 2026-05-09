package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import io.github.kentasun.janus.starter.JanusThrottlingTests;
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
