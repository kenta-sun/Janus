package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.AbstractDataJanusPlugin;
import io.github.kentasun.janus.starter.annotation.TestAnnotation;
import org.springframework.stereotype.Component;

@Component
public class TestAnnotationJanusPlugin extends AbstractDataJanusPlugin<TestAnnotationJanusPlugin.TestAnnotationJanusPluginData> {
    @Override
    public void switchBranch(JanusContext context) {
        TestAnnotation annotation = context.getAnnotation(TestAnnotation.class);
        TestAnnotationJanusPluginData pluginData = this.getPluginData(context);
        pluginData.setValue(annotation.value());
    }

    public static class TestAnnotationJanusPluginData {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
