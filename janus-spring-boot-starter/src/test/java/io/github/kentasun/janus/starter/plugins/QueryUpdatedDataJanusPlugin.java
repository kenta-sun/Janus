package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import org.springframework.stereotype.Component;

@Component
public class QueryUpdatedDataJanusPlugin implements JanusPlugin {

    @Override
    public int getOrder() {
        return 10000;
    }

    @Override
    public void afterPrimaryExecute(JanusContext context) {
        context.getPrimaryBranch().getBranchRes().setQueryRes("2");
    }

    @Override
    public void afterSecondaryExecute(JanusContext context) {
        context.getPrimaryBranch().getBranchRes().setQueryRes("2");
    }
}
