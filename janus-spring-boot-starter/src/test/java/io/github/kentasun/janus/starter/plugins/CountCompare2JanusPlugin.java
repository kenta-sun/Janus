package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import io.github.kentasun.janus.starter.CompareThrottlingTests;
import io.github.kentasun.janus.starter.dto.TestRequest;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CountCompare2JanusPlugin implements JanusPlugin {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CountCompare2JanusPlugin.class);

    @Override
    public void afterCompare(JanusContext context) {
        TestRequest request = (TestRequest) context.getArgs()[0];
        log.debug("CountCompare2JanusPlugin：{}", request.getKey());
        CompareThrottlingTests.longAdder.increment();
    }
}
