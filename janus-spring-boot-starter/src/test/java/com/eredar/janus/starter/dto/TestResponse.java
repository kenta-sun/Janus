package com.eredar.janus.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TestResponse {

    private Integer number;

    private String ignoreStr1;

    private List<TestIgnoreDTO> ignoreList;

}
