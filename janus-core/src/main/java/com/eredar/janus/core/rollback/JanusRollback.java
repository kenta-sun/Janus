package com.eredar.janus.core.rollback;

import com.eredar.janus.core.dto.JanusContext;

public interface JanusRollback {

    void branchRollback(JanusContext context, String lifecycle, Runnable runnable);
}
