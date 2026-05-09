package io.github.kentasun.janus.core.dto;

import lombok.*;

/**
 * 分支运行结果对象
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BranchRes {

    // 分支方法返回值
    private Object res;
    // 分支方法运行结束后，通过查询数据库得到的分支方法对数据库的修改
    @Setter
    private Object queryRes;
}
