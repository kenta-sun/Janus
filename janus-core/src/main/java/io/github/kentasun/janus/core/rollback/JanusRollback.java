package io.github.kentasun.janus.core.rollback;

import io.github.kentasun.janus.core.dto.JanusContext;

public interface JanusRollback {

    void branchRollback(JanusContext context, String lifecycle, Runnable runnable);
}
