package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

@Component
public class CompareThrottling2JanusPlugin implements JanusPlugin {

    // 初始值为 0
    private final LongAdder longAdder = new LongAdder();

    @Override
    public void switchBranch(JanusContext context) {
        longAdder.increment();
        int value = longAdder.intValue();
        if (value <= 11) {
            context.setNeedCompare(false);
        } else {
            context.setNeedCompare(true);
        }
    }
}
