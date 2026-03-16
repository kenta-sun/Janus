package com.eredar.janus.core.compare;

import com.eredar.janus.core.dto.CompareRes;
import com.eredar.janus.core.dto.JanusContext;

/**
 * Janus 比对功能抽象
 */
public interface JanusCompare {

    CompareRes compare(JanusContext context);
}
