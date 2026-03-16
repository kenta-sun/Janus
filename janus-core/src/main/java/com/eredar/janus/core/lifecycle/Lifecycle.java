package com.eredar.janus.core.lifecycle;

import com.eredar.janus.core.dto.JanusContextImpl;

/**
 * Janus 生命周期接口
 */
public interface Lifecycle {

    void switchBranch(JanusContextImpl context);

    void primaryExecute(JanusContextImpl context);

    void secondaryExecute(JanusContextImpl context);

    void compare(JanusContextImpl context);
}
