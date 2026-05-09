package io.github.kentasun.janus.starter.plugins;

import io.github.kentasun.janus.core.dto.JanusContext;
import io.github.kentasun.janus.core.plugin.AbstractDataJanusPlugin;
import io.github.kentasun.janus.starter.annotation.TestAnnotation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class TestAnnotationJanusPlugin extends AbstractDataJanusPlugin<TestAnnotationJanusPlugin.TestAnnotationJanusPluginData> {
    @Override
    public void switchBranch(JanusContext context) {
        TestAnnotation annotation = context.getAnnotation(TestAnnotation.class);
        TestAnnotationJanusPluginData pluginData = this.getPluginData(context);
        pluginData.setValue(annotation.value());
    }

    @Setter
    @Getter
    public static class TestAnnotationJanusPluginData {
        private String value;
    }
}
