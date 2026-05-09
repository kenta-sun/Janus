package io.github.kentasun.janus.core.flow;

import io.github.kentasun.janus.core.constants.JanusCompareType;
import io.github.kentasun.janus.core.dto.JanusContextImpl;

/**
 * 根据 {@link JanusCompareType} 编排分流比对流程
 */
public interface JanusFlow {

    String getCompareType();

    void execute(JanusContextImpl context);
}
