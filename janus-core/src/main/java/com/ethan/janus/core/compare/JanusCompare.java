package com.ethan.janus.core.compare;

import com.ethan.janus.core.dto.BranchInfo;
import com.ethan.janus.core.dto.CompareRes;

/**
 * Janus 比对功能抽象
 */
public interface JanusCompare {

    CompareRes compare(BranchInfo primaryBranch, BranchInfo secondaryBranch);
}
