package com.eredar.janus.starter.plugins;

import com.eredar.janus.core.constants.JanusConstants;
import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.plugin.JanusPlugin;
import org.springframework.stereotype.Component;

@Component
public class SwitchJanusPlugin implements JanusPlugin {

    @Override
    public void switchBranch(JanusContext context) {
        context.setMasterBranchName(JanusConstants.PRIMARY);
    }
}
