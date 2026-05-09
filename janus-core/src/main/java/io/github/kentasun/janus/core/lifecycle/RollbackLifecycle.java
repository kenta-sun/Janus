package io.github.kentasun.janus.core.lifecycle;

import io.github.kentasun.janus.core.dto.JanusContextImpl;
import io.github.kentasun.janus.core.rollback.JanusRollback;
import org.springframework.beans.factory.annotation.Autowired;

public class RollbackLifecycle extends LifecycleDecorator {

    @Autowired
    private JanusRollback janusRollback;

    @Override
    public void switchBranch(JanusContextImpl context) {
        decoratedLifecycle.switchBranch(context);
    }

    @Override
    public void primaryExecute(JanusContextImpl context) {
        // 被设置回滚标识则会回滚
        if (context.getPrimaryBranch().getIsRollback()) {
            janusRollback.branchRollback(context, "primaryExecute", () -> decoratedLifecycle.primaryExecute(context));
        } else { // 没有回滚标识会提交事务
            decoratedLifecycle.primaryExecute(context);
        }
    }

    @Override
    public void secondaryExecute(JanusContextImpl context) {
        // 被设置回滚标识则会回滚
        if (context.getSecondaryBranch().getIsRollback()) {
            janusRollback.branchRollback(context, "secondaryExecute", () -> decoratedLifecycle.secondaryExecute(context));
        } else { // 没有回滚标识会提交事务
            decoratedLifecycle.secondaryExecute(context);
        }
    }

    @Override
    public void compare(JanusContextImpl context) {
        decoratedLifecycle.compare(context);
    }
}
