package io.github.kentasun.janus.core.compare;

import io.github.kentasun.janus.core.dto.CompareRes;
import io.github.kentasun.janus.core.dto.JanusContext;

/**
 * Janus 比对功能抽象
 */
public interface JanusCompare {

    CompareRes compare(JanusContext context);
}
