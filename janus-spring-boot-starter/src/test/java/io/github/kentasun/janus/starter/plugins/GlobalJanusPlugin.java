package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.annotation.Global;
import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import io.github.kentasun.janus.starter.JanusTests;
import org.springframework.stereotype.Component;

@Global
@Component
public class GlobalJanusPlugin implements JanusPlugin {

    @Override
    public void switchBranch(JanusContext context) {
        JanusTests.globalString = "Global";
    }
}
