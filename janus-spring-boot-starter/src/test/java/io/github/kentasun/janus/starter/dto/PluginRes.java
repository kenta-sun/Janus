package io.github.kentasun.janus.starter.dto;

import io.github.kentasun.janus.core.dto.CompareRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PluginRes {

    public String methodId;
    public String masterBranchName;
    public Long primaryTime;
    public Long secondaryTime;
    public CompareRes compareRes;
    public String businessKey;
    public String testAnnotationKey;
    public Integer resTblNum;
}
