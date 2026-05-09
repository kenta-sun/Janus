package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import io.github.kentasun.janus.starter.CompareThrottlingTests;
import io.github.kentasun.janus.starter.dto.TestRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountCompare2JanusPlugin implements JanusPlugin {

    @Override
    public void afterCompare(JanusContext context) {
        TestRequest request = (TestRequest) context.getArgs()[0];
        log.debug("CountCompare2JanusPlugin：{}", request.getKey());
        CompareThrottlingTests.longAdder.increment();
    }
}
