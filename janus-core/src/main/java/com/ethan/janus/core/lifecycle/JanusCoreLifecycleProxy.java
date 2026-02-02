package com.ethan.janus.core.lifecycle;

import com.ethan.janus.core.utils.JanusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * 核心生命周期静态代理类。
 * <P>1. 每次进入aop都要new一个新的代理类，以此来更新pluginList
 * <P>2. 作为代理类，增强的功能为：在正确的生命周期节点执行配置的插件。
 */
@Component
public class JanusCoreLifecycleProxy implements JanusLifecycle{

    @Autowired
    private JanusLifecycle janusCoreLifecycle;

    @Override
    public void switchBranch(JanusContext context) {
        this.executePluginList(context, plugin -> plugin.switchBranch(context));
        janusCoreLifecycle.switchBranch(context);
    }

    @Override
    public void primaryExecute(JanusContext context) {
        this.executePluginList(context, plugin -> plugin.beforePrimaryExecute(context));
        janusCoreLifecycle.primaryExecute(context);
        this.executePluginList(context, plugin -> plugin.afterPrimaryExecute(context));
    }

    @Override
    public void secondaryExecute(JanusContext context) {
        this.executePluginList(context, plugin -> plugin.beforeSecondaryExecute(context));
        janusCoreLifecycle.secondaryExecute(context);
        this.executePluginList(context, plugin -> plugin.afterSecondaryExecute(context));
    }

    @Override
    public void compare(JanusContext context) {
        this.executePluginList(context, plugin -> plugin.beforeCompare(context));
        janusCoreLifecycle.compare(context);
        this.executePluginList(context, plugin -> plugin.afterCompare(context));
    }

    /**
     * 批量执行插件列表
     *
     * @param context 上下文对象
     * @param consumer 被期望被执行的方法
     */
    private void executePluginList(JanusContext context, Consumer<JanusPlugin> consumer) {
        List<JanusPlugin> pluginList = context.getPluginList();
        if (JanusUtils.isNotEmpty(pluginList)) {
            pluginList.forEach(consumer);
        }
    }
}
