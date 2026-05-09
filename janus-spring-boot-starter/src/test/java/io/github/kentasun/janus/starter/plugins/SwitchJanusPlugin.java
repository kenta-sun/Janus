package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.constants.JanusConstants;
import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import org.springframework.stereotype.Component;

@Component
public class SwitchJanusPlugin implements JanusPlugin {

    @Override
    public void switchBranch(JanusContext context) {
        context.setMasterBranchName(JanusConstants.PRIMARY);
    }
}
