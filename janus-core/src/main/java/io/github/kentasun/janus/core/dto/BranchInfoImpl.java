package io.github.kentasun.janus.core.dto;

import lombok.*;

/**
 * 分流比对框架 分支信息
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BranchInfoImpl implements BranchInfo {

    // 分支类型: primary-主分支; secondary-次要分支
    private String branchType;

    // 是否已经执行过: true-执行过; false 或者 null，都表示未执行过
    @Setter
    private Boolean isExecuted;
    // 分支运行结果对象
    @Setter
    private BranchRes branchRes;
    // 异常对象
    @Setter
    private Throwable exception;
    /**
     * 是否做事务回滚：如果为true，Janus框架会强制添加事务并回滚分支执行的部分。
     * <p>1. 如果已经存在事务，会采用已经存在的事务。</p>
     * <p>2. 不会回滚整个事务，仅回滚分支执行的部分。</p>
     */
    @Setter
    private Boolean isRollback;
    // 是否是异步执行
    @Setter
    private Boolean isAsync;

    /**
     * 是否成功
     * @return true-成功；false-不成功
     */
    public boolean isSuccess() {
        return exception == null;
    }

    /**
     * 是否报错
     * @return true-报错；false-没报错，成功返回结果
     */
    public boolean isError() {
        return exception != null;
    }
}
