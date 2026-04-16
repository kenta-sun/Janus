package com.eredar.janus.core.lifecycle;

import com.eredar.janus.core.aspect.JanusAspect;
import com.eredar.janus.core.dto.JanusContextImpl;

/**
 * 用于组合组合所有生命周期，并且注入{@link JanusAspect}
 * <p>注意，该类不可添加任何额外功能，仅用于组合装饰模式的所有实现
 */
public class LifecycleDecoratorManager extends LifecycleDecorator {

    public LifecycleDecoratorManager(CoreLifecycle coreLifecycle,
                                     TimeLifecycle timeLifecycle,
                                     RollbackLifecycle rollbackLifecycle,
                                     HigherPluginsExecuteLifecycle higherPluginsExecuteLifecycle,
                                     LowerPluginsExecuteLifecycle lowerPluginsExecuteLifecycle) {
        // 装饰模式，组合所有生命周期
        this.setDecoratedLifecycle(higherPluginsExecuteLifecycle)
                .setDecoratedLifecycle(rollbackLifecycle)
                .setDecoratedLifecycle(lowerPluginsExecuteLifecycle)
                .setDecoratedLifecycle(timeLifecycle)
                .setDecoratedLifecycle(coreLifecycle);
    }

    @Override
    public void switchBranch(JanusContextImpl context) {
        decoratedLifecycle.switchBranch(context);
    }

    @Override
    public void primaryExecute(JanusContextImpl context) {
        decoratedLifecycle.primaryExecute(context);
    }

    @Override
    public void secondaryExecute(JanusContextImpl context) {
        decoratedLifecycle.secondaryExecute(context);
    }

    @Override
    public void compare(JanusContextImpl context) {
        decoratedLifecycle.compare(context);
    }
}
