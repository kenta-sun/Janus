package io.github.kentasun.janus.core.dto;

/**
 * 分流比对框架 分支信息
 */
public class BranchInfoImpl implements BranchInfo {

    // 分支类型: primary-主分支; secondary-次要分支
    private String branchType;

    // 是否已经执行过: true-执行过; false 或者 null，都表示未执行过
    private Boolean isExecuted;
    // 分支运行结果对象
    private BranchRes branchRes;
    // 异常对象
    private Throwable exception;
    /**
     * 是否做事务回滚：如果为true，Janus框架会强制添加事务并回滚分支执行的部分。
     * <p>1. 如果已经存在事务，会采用已经存在的事务。</p>
     * <p>2. 不会回滚整个事务，仅回滚分支执行的部分。</p>
     */
    private Boolean isRollback;
    // 是否是异步执行
    private Boolean isAsync;

    public BranchInfoImpl(final String branchType, final Boolean isExecuted, final BranchRes branchRes, final Throwable exception, final Boolean isRollback, final Boolean isAsync) {
        this.branchType = branchType;
        this.isExecuted = isExecuted;
        this.branchRes = branchRes;
        this.exception = exception;
        this.isRollback = isRollback;
        this.isAsync = isAsync;
    }

    public BranchInfoImpl() {
    }

    /**
     * 是否成功
     *
     * @return true-成功；false-不成功
     */
    public boolean isSuccess() {
        return exception == null;
    }

    /**
     * 是否报错
     *
     * @return true-报错；false-没报错，成功返回结果
     */
    public boolean isError() {
        return exception != null;
    }

    @Override
    public String getBranchType() {
        return branchType;
    }

    public Boolean getIsExecuted() {
        return isExecuted;
    }

    @Override
    public BranchRes getBranchRes() {
        return branchRes;
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    public Boolean getIsRollback() {
        return isRollback;
    }

    public Boolean getIsAsync() {
        return isAsync;
    }

    public void setIsExecuted(Boolean executed) {
        isExecuted = executed;
    }

    public void setBranchRes(BranchRes branchRes) {
        this.branchRes = branchRes;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public void setIsRollback(Boolean rollback) {
        isRollback = rollback;
    }

    public void setIsAsync(Boolean async) {
        isAsync = async;
    }

    @Override
    public String toString() {
        return "BranchInfoImpl{" +
                "branchType='" + branchType + '\'' +
                ", isExecuted=" + isExecuted +
                ", branchRes=" + branchRes +
                ", exception=" + exception +
                ", isRollback=" + isRollback +
                ", isAsync=" + isAsync +
                '}';
    }

    public static BranchInfoImplBuilder builder() {
        return new BranchInfoImplBuilder();
    }

    public static class BranchInfoImplBuilder {
        private String branchType;
        private Boolean isExecuted;
        private BranchRes branchRes;
        private Throwable exception;
        private Boolean isRollback;
        private Boolean isAsync;

        BranchInfoImplBuilder() {
        }

        public BranchInfoImplBuilder branchType(final String branchType) {
            this.branchType = branchType;
            return this;
        }

        public BranchInfoImplBuilder isExecuted(final Boolean isExecuted) {
            this.isExecuted = isExecuted;
            return this;
        }

        public BranchInfoImplBuilder branchRes(final BranchRes branchRes) {
            this.branchRes = branchRes;
            return this;
        }

        public BranchInfoImplBuilder exception(final Throwable exception) {
            this.exception = exception;
            return this;
        }

        public BranchInfoImplBuilder isRollback(final Boolean isRollback) {
            this.isRollback = isRollback;
            return this;
        }

        public BranchInfoImplBuilder isAsync(final Boolean isAsync) {
            this.isAsync = isAsync;
            return this;
        }

        public BranchInfoImpl build() {
            return new BranchInfoImpl(this.branchType, this.isExecuted, this.branchRes, this.exception, this.isRollback, this.isAsync);
        }

        @Override
        public String toString() {
            return "BranchInfoImplBuilder{" +
                    "branchType='" + branchType + '\'' +
                    ", isExecuted=" + isExecuted +
                    ", branchRes=" + branchRes +
                    ", exception=" + exception +
                    ", isRollback=" + isRollback +
                    ", isAsync=" + isAsync +
                    '}';
        }
    }
}
