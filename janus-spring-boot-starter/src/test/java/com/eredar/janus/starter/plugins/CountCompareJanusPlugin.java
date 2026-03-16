package com.eredar.janus.starter.plugins;

import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.plugin.JanusPlugin;
import com.eredar.janus.starter.JanusThrottlingTests;
import org.springframework.stereotype.Component;

@Component
public class CountCompareJanusPlugin implements JanusPlugin {

    @Override
    public void afterCompare(JanusContext context) {
        JanusThrottlingTests.longAdder.increment();
    }
}
