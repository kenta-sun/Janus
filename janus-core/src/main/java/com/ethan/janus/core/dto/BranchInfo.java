package com.ethan.janus.core.dto;

public interface BranchInfo {

    Boolean getIsExecuted();

    String getBranchType();

    Object getBranchRes();

    Throwable getException();

    Boolean getIsRollback();

    Boolean getIsAsync();
}
