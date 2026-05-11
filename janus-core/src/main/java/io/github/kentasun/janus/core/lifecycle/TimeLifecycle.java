package io.github.kentasun.janus.core.lifecycle;

import io.github.kentasun.janus.core.dto.JanusContextImpl;
import io.github.kentasun.janus.core.utils.JanusLogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统计分支耗时，精确到纳秒
 */
public class TimeLifecycle extends LifecycleDecorator {

    private static final Logger log = LoggerFactory.getLogger(TimeLifecycle.class);

    @Override
    public void switchBranch(JanusContextImpl context) {
        decoratedLifecycle.switchBranch(context);
    }

    @Override
    public void primaryExecute(JanusContextImpl context) {
        long time = System.nanoTime();
        decoratedLifecycle.primaryExecute(context);
        time = System.nanoTime() - time;
        context.setPrimaryTime(time);
        log.debug(
                "[Janus] {} [methodId:{}] [businessKey:{}] [lifecycle:primaryExecute] >> timeTaken={}ns",
                JanusLogUtils.SUCCESS_ICON,
                context.getMethodId(),
                context.getBusinessKey(),
                time
        );
    }

    @Override
    public void secondaryExecute(JanusContextImpl context) {
        long time = System.nanoTime();
        decoratedLifecycle.secondaryExecute(context);
        time = System.nanoTime() - time;
        context.setSecondaryTime(time);
        log.debug(
                "[Janus] {} [methodId:{}] [businessKey:{}] [lifecycle:secondaryExecute] >> timeTaken={}ns",
                JanusLogUtils.SUCCESS_ICON,
                context.getMethodId(),
                context.getBusinessKey(),
                time
        );
    }

    @Override
    public void compare(JanusContextImpl context) {
        decoratedLifecycle.compare(context);
    }
}
