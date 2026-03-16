package com.eredar.janus.core.dto;

import lombok.*;

import java.util.Map;

/**
 * Janus 比对结果
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompareRes {

    // 比对结果状态
    private String compareStatus;
    // 2个分支都成功时，比对发现的差异字段集合
    private Map<String, String> diffFieldMap;
}
