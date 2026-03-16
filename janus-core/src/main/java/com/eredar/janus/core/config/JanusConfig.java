package com.eredar.janus.core.config;

import com.eredar.janus.core.compare.JanusCompare;
import com.eredar.janus.core.compare.JanusCompareDefaultImpl;
import com.eredar.janus.core.rollback.JanusRollback;
import com.eredar.janus.core.rollback.JanusRollbackDefault;
import com.eredar.janus.core.threadpool.JanusBranchThreadPoolDefaultProvider;
import com.eredar.janus.core.threadpool.JanusBranchThreadPoolMetricsProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JanusConfig {

    @Bean
    @ConditionalOnMissingBean(value = JanusRollback.class)
    public JanusRollback janusRollback() {
        return new JanusRollbackDefault();
    }

    @Bean
    @ConditionalOnMissingBean(value = JanusCompare.class)
    public JanusCompare defaultJanusCompare() {
        return new JanusCompareDefaultImpl();
    }

    @Bean
    @ConditionalOnMissingBean(value = JanusBranchThreadPoolMetricsProvider.class)
    public JanusBranchThreadPoolMetricsProvider janusBranchThreadPoolMetricsProvider() {
        return new JanusBranchThreadPoolDefaultProvider();
    }
}
