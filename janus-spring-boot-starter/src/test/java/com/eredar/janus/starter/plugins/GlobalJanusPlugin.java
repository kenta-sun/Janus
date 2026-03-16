package com.eredar.janus.starter.plugins;

import com.eredar.janus.core.annotation.Global;
import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.plugin.JanusPlugin;
import com.eredar.janus.starter.JanusTests;
import org.springframework.stereotype.Component;

@Global
@Component
public class GlobalJanusPlugin implements JanusPlugin {

    @Override
    public void switchBranch(JanusContext context) {
        JanusTests.globalString = "Global";
    }
}
