package io.github.kentasun.janus.core.dto;

import lombok.*;

/**
 * Janus 切面状态，用于传递到切面外。
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JanusAspectStatus {

    // 主分支名
    private String masterBranchName;
}
