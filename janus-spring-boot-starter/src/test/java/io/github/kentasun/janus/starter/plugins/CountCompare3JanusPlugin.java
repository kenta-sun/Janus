package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import io.github.kentasun.janus.starter.CompareThrottlingTests;
import io.github.kentasun.janus.starter.dto.TestRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CountCompare3JanusPlugin implements JanusPlugin {

    private static final Logger log = LoggerFactory.getLogger(CountCompare3JanusPlugin.class);

    @Override
    public void afterCompare(JanusContext context) {
        TestRequest request = (TestRequest) context.getArgs()[0];
        log.debug("CountCompare3JanusPlugin：{}", request.getKey());
        CompareThrottlingTests.longAdder2.increment();
    }
}
