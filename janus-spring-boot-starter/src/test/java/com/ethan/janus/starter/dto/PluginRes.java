package com.ethan.janus.starter.dto;

import com.ethan.janus.core.dto.CompareRes;


public class PluginRes {

    public String methodId = null;
    public Long primaryTime = null;
    public Long secondaryTime = null;
    public CompareRes compareRes = null;
    public String businessKey = null;
    public String testAnnotationKey = null;

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public Long getPrimaryTime() {
        return primaryTime;
    }

    public void setPrimaryTime(Long primaryTime) {
        this.primaryTime = primaryTime;
    }

    public Long getSecondaryTime() {
        return secondaryTime;
    }

    public void setSecondaryTime(Long secondaryTime) {
        this.secondaryTime = secondaryTime;
    }

    public CompareRes getCompareRes() {
        return compareRes;
    }

    public void setCompareRes(CompareRes compareRes) {
        this.compareRes = compareRes;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTestAnnotationKey() {
        return testAnnotationKey;
    }

    public void setTestAnnotationKey(String testAnnotationKey) {
        this.testAnnotationKey = testAnnotationKey;
    }
}
