package io.github.kentasun.janus.core.dto;

import io.github.kentasun.janus.core.plugin.JanusPlugin;

import java.util.List;

public class PluginListDTO {

    // 高优先级插件，order 小于0
    private List<JanusPlugin> higherPluginList;
    // 低优先级插件，order 大于0
    private List<JanusPlugin> lowerPluginList;

    public PluginListDTO(final List<JanusPlugin> higherPluginList, final List<JanusPlugin> lowerPluginList) {
        this.higherPluginList = higherPluginList;
        this.lowerPluginList = lowerPluginList;
    }

    public PluginListDTO() {
    }

    public List<JanusPlugin> getHigherPluginList() {
        return higherPluginList;
    }

    public void setHigherPluginList(List<JanusPlugin> higherPluginList) {
        this.higherPluginList = higherPluginList;
    }

    public List<JanusPlugin> getLowerPluginList() {
        return lowerPluginList;
    }

    public void setLowerPluginList(List<JanusPlugin> lowerPluginList) {
        this.lowerPluginList = lowerPluginList;
    }

    @Override
    public String toString() {
        return "PluginListDTO{" +
                "higherPluginList=" + higherPluginList +
                ", lowerPluginList=" + lowerPluginList +
                '}';
    }

    public static PluginListDTOBuilder builder() {
        return new PluginListDTOBuilder();
    }

    public static class PluginListDTOBuilder {
        private List<JanusPlugin> higherPluginList;
        private List<JanusPlugin> lowerPluginList;

        PluginListDTOBuilder() {
        }

        public PluginListDTOBuilder higherPluginList(final List<JanusPlugin> higherPluginList) {
            this.higherPluginList = higherPluginList;
            return this;
        }

        public PluginListDTOBuilder lowerPluginList(final List<JanusPlugin> lowerPluginList) {
            this.lowerPluginList = lowerPluginList;
            return this;
        }

        public PluginListDTO build() {
            return new PluginListDTO(this.higherPluginList, this.lowerPluginList);
        }

        @Override
        public String toString() {
            return "PluginListDTOBuilder{" +
                    "higherPluginList=" + higherPluginList +
                    ", lowerPluginList=" + lowerPluginList +
                    '}';
        }
    }
}
