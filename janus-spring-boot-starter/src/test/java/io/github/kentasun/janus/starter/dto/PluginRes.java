package io.github.kentasun.janus.starter.dto;

import io.github.kentasun.janus.core.dto.CompareRes;

public class PluginRes {

    public String methodId;
    public String masterBranchName;
    public Long primaryTime;
    public Long secondaryTime;
    public CompareRes compareRes;
    public String businessKey;
    public String testAnnotationKey;
    public Integer resTblNum;

    public PluginRes(String methodId, String masterBranchName, Long primaryTime, Long secondaryTime, CompareRes compareRes, String businessKey, String testAnnotationKey, Integer resTblNum) {
        this.methodId = methodId;
        this.masterBranchName = masterBranchName;
        this.primaryTime = primaryTime;
        this.secondaryTime = secondaryTime;
        this.compareRes = compareRes;
        this.businessKey = businessKey;
        this.testAnnotationKey = testAnnotationKey;
        this.resTblNum = resTblNum;
    }

    public PluginRes() {
    }

    public static PluginResBuilder builder() {
        return new PluginResBuilder();
    }

    public String getMethodId() {
        return this.methodId;
    }

    public String getMasterBranchName() {
        return this.masterBranchName;
    }

    public Long getPrimaryTime() {
        return this.primaryTime;
    }

    public Long getSecondaryTime() {
        return this.secondaryTime;
    }

    public CompareRes getCompareRes() {
        return this.compareRes;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public String getTestAnnotationKey() {
        return this.testAnnotationKey;
    }

    public Integer getResTblNum() {
        return this.resTblNum;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public void setMasterBranchName(String masterBranchName) {
        this.masterBranchName = masterBranchName;
    }

    public void setPrimaryTime(Long primaryTime) {
        this.primaryTime = primaryTime;
    }

    public void setSecondaryTime(Long secondaryTime) {
        this.secondaryTime = secondaryTime;
    }

    public void setCompareRes(CompareRes compareRes) {
        this.compareRes = compareRes;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public void setTestAnnotationKey(String testAnnotationKey) {
        this.testAnnotationKey = testAnnotationKey;
    }

    public void setResTblNum(Integer resTblNum) {
        this.resTblNum = resTblNum;
    }

    @Override
    public String toString() {
        return "PluginRes{" +
                "methodId='" + methodId + '\'' +
                ", masterBranchName='" + masterBranchName + '\'' +
                ", primaryTime=" + primaryTime +
                ", secondaryTime=" + secondaryTime +
                ", compareRes=" + compareRes +
                ", businessKey='" + businessKey + '\'' +
                ", testAnnotationKey='" + testAnnotationKey + '\'' +
                ", resTblNum=" + resTblNum +
                '}';
    }

    public static class PluginResBuilder {
        private String methodId;
        private String masterBranchName;
        private Long primaryTime;
        private Long secondaryTime;
        private CompareRes compareRes;
        private String businessKey;
        private String testAnnotationKey;
        private Integer resTblNum;

        PluginResBuilder() {
        }

        public PluginResBuilder methodId(String methodId) {
            this.methodId = methodId;
            return this;
        }

        public PluginResBuilder masterBranchName(String masterBranchName) {
            this.masterBranchName = masterBranchName;
            return this;
        }

        public PluginResBuilder primaryTime(Long primaryTime) {
            this.primaryTime = primaryTime;
            return this;
        }

        public PluginResBuilder secondaryTime(Long secondaryTime) {
            this.secondaryTime = secondaryTime;
            return this;
        }

        public PluginResBuilder compareRes(CompareRes compareRes) {
            this.compareRes = compareRes;
            return this;
        }

        public PluginResBuilder businessKey(String businessKey) {
            this.businessKey = businessKey;
            return this;
        }

        public PluginResBuilder testAnnotationKey(String testAnnotationKey) {
            this.testAnnotationKey = testAnnotationKey;
            return this;
        }

        public PluginResBuilder resTblNum(Integer resTblNum) {
            this.resTblNum = resTblNum;
            return this;
        }

        public PluginRes build() {
            return new PluginRes(this.methodId, this.masterBranchName, this.primaryTime, this.secondaryTime, this.compareRes, this.businessKey, this.testAnnotationKey, this.resTblNum);
        }

        @Override
        public String toString() {
            return "PluginResBuilder{" +
                    "methodId='" + methodId + '\'' +
                    ", masterBranchName='" + masterBranchName + '\'' +
                    ", primaryTime=" + primaryTime +
                    ", secondaryTime=" + secondaryTime +
                    ", compareRes=" + compareRes +
                    ", businessKey='" + businessKey + '\'' +
                    ", testAnnotationKey='" + testAnnotationKey + '\'' +
                    ", resTblNum=" + resTblNum +
                    '}';
        }
    }
}
