package io.github.kentasun.janus.core.dto;

import java.util.Map;

/**
 * Janus 比对结果
 */
public class CompareRes {

    // 比对结果状态
    private String compareStatus;
    // 2个分支都成功时，比对发现的差异字段集合
    private Map<String, String> diffFieldMap;

    public static CompareResBuilder builder() {
        return new CompareResBuilder();
    }

    public CompareRes(final String compareStatus, final Map<String, String> diffFieldMap) {
        this.compareStatus = compareStatus;
        this.diffFieldMap = diffFieldMap;
    }

    public CompareRes() {
    }

    public String getCompareStatus() {
        return this.compareStatus;
    }

    public Map<String, String> getDiffFieldMap() {
        return this.diffFieldMap;
    }

    public void setCompareStatus(final String compareStatus) {
        this.compareStatus = compareStatus;
    }

    public void setDiffFieldMap(final Map<String, String> diffFieldMap) {
        this.diffFieldMap = diffFieldMap;
    }

    @Override
    public String toString() {
        return "CompareRes{" +
                "compareStatus='" + compareStatus + '\'' +
                ", diffFieldMap=" + diffFieldMap +
                '}';
    }

    public static class CompareResBuilder {
        private String compareStatus;
        private Map<String, String> diffFieldMap;

        CompareResBuilder() {
        }

        public CompareResBuilder compareStatus(final String compareStatus) {
            this.compareStatus = compareStatus;
            return this;
        }

        public CompareResBuilder diffFieldMap(final Map<String, String> diffFieldMap) {
            this.diffFieldMap = diffFieldMap;
            return this;
        }

        public CompareRes build() {
            return new CompareRes(this.compareStatus, this.diffFieldMap);
        }

        @Override
        public String toString() {
            return "CompareResBuilder{" +
                    "compareStatus='" + compareStatus + '\'' +
                    ", diffFieldMap=" + diffFieldMap +
                    '}';
        }
    }
}
