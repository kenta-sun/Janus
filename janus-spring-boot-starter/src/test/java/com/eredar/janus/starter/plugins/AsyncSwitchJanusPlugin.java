package com.eredar.janus.starter.plugins;

import com.eredar.janus.core.constants.JanusConstants;
import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.plugin.JanusPlugin;
import com.eredar.janus.starter.dto.TestRequest;
import org.springframework.stereotype.Component;

@Component
public class AsyncSwitchJanusPlugin implements JanusPlugin {

    @Override
    public void switchBranch(JanusContext context) {
        Object[] args = context.getArgs();
        TestRequest request = (TestRequest) args[0];
        if ("primary".equals(request.getKey())) {
            context.setMasterBranchName(JanusConstants.PRIMARY);
        } else {
            context.setMasterBranchName(JanusConstants.SECONDARY);
        }
    }
}
