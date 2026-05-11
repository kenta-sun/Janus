package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.AbstractDataJanusPlugin;
import io.github.kentasun.janus.core.utils.JanusJsonUtils;
import io.github.kentasun.janus.starter.JanusTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;

@Component
public class ExecuteTimeJanusPlugin extends AbstractDataJanusPlugin<ExecuteTimeJanusPlugin.ExecuteTimeJanusPluginData> {

    private static final Logger log = LoggerFactory.getLogger(ExecuteTimeJanusPlugin.class);

    @Override
    public void beforeCompare(JanusContext context) {
        // primary 分支耗时
        JanusTests.pluginRes.primaryTime = context.getPrimaryTime();
        // secondary 分支耗时
        JanusTests.pluginRes.secondaryTime = context.getSecondaryTime();
    }

    @Override
    public void afterCompare(JanusContext context) {
        JanusTests.pluginRes.methodId = context.getMethodId();
        JanusTests.pluginRes.masterBranchName = context.getMasterBranchName();
        JanusTests.pluginRes.compareRes = context.getCompareRes();
        JanusTests.pluginRes.businessKey = context.getBusinessKey();
        TestAnnotationJanusPlugin.TestAnnotationJanusPluginData testAnnotationJanusPluginData = context.getOtherPluginData(TestAnnotationJanusPlugin.class);
        if (testAnnotationJanusPluginData != null) {
            JanusTests.pluginRes.testAnnotationKey = testAnnotationJanusPluginData.getValue();
        }

        log.debug(
                "primaryRes={}, secondaryRes={}, compareRes={}",
                JanusJsonUtils.writeValueAsString(context.getPrimaryBranch().getBranchRes()),
                JanusJsonUtils.writeValueAsString(context.getSecondaryBranch().getBranchRes()),
                JanusJsonUtils.writeValueAsString(context.getCompareRes())
        );
    }

    public static class ExecuteTimeJanusPluginData {

        private LocalDateTime primaryStartDate;
        private LocalDateTime primaryEndDate;
        private LocalDateTime secondaryStartDate;
        private LocalDateTime secondaryEndDate;
        private StopWatch primaryStopWatch;
        private StopWatch secondaryStopWatch;

        public ExecuteTimeJanusPluginData() {
        }

        public LocalDateTime getPrimaryStartDate() {
            return this.primaryStartDate;
        }

        public LocalDateTime getPrimaryEndDate() {
            return this.primaryEndDate;
        }

        public LocalDateTime getSecondaryStartDate() {
            return this.secondaryStartDate;
        }

        public LocalDateTime getSecondaryEndDate() {
            return this.secondaryEndDate;
        }

        public StopWatch getPrimaryStopWatch() {
            return this.primaryStopWatch;
        }

        public StopWatch getSecondaryStopWatch() {
            return this.secondaryStopWatch;
        }

        public void setPrimaryStartDate(LocalDateTime primaryStartDate) {
            this.primaryStartDate = primaryStartDate;
        }

        public void setPrimaryEndDate(LocalDateTime primaryEndDate) {
            this.primaryEndDate = primaryEndDate;
        }

        public void setSecondaryStartDate(LocalDateTime secondaryStartDate) {
            this.secondaryStartDate = secondaryStartDate;
        }

        public void setSecondaryEndDate(LocalDateTime secondaryEndDate) {
            this.secondaryEndDate = secondaryEndDate;
        }

        public void setPrimaryStopWatch(StopWatch primaryStopWatch) {
            this.primaryStopWatch = primaryStopWatch;
        }

        public void setSecondaryStopWatch(StopWatch secondaryStopWatch) {
            this.secondaryStopWatch = secondaryStopWatch;
        }

        @Override
        public String toString() {
            return "ExecuteTimeJanusPluginData{" +
                    "primaryStartDate=" + primaryStartDate +
                    ", primaryEndDate=" + primaryEndDate +
                    ", secondaryStartDate=" + secondaryStartDate +
                    ", secondaryEndDate=" + secondaryEndDate +
                    ", primaryStopWatch=" + primaryStopWatch +
                    ", secondaryStopWatch=" + secondaryStopWatch +
                    '}';
        }
    }
}
