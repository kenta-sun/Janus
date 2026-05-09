package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.constants.JanusConstants;
import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import io.github.kentasun.janus.starter.dto.TestRequest;
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
