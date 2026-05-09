package io.github.kentasun.janus.core.lifecycle;

import io.github.kentasun.janus.core.dto.JanusContextImpl;

/**
 * Janus 生命周期接口
 */
public interface Lifecycle {

    void switchBranch(JanusContextImpl context);

    void primaryExecute(JanusContextImpl context);

    void secondaryExecute(JanusContextImpl context);

    void compare(JanusContextImpl context);
}
