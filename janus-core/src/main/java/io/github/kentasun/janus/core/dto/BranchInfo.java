package io.github.kentasun.janus.core.dto;

public interface BranchInfo {

    Boolean getIsExecuted();

    String getBranchType();

    BranchRes getBranchRes();

    Throwable getException();

    Boolean getIsRollback();

    Boolean getIsAsync();

    boolean isSuccess();

    boolean isError();
}
