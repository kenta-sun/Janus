package com.ethan.janus.core.dto;

import com.ethan.janus.core.constants.CompareType;

public interface JanusContext {

    String getMethodId();
    String getBusinessKey();
    CompareType getCompareType();
    Boolean getIsAsyncCompare();
    String getMasterBranchName();
    BranchInfo getPrimaryBranch();
    BranchInfo getSecondaryBranch();
    BranchInfo getMasterBranch();
    BranchInfo getCompareBranch();
    CompareRes getCompareRes();

    void setMasterBranchName(String masterBranchName);
}
