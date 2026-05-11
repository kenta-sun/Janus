package io.github.kentasun.janus.core.dto;

/**
 * 分支运行结果对象
 */
public class BranchRes {

    // 分支方法返回值
    private Object res;
    // 分支方法运行结束后，通过查询数据库得到的分支方法对数据库的修改
    private Object queryRes;

    public BranchRes(final Object res, final Object queryRes) {
        this.res = res;
        this.queryRes = queryRes;
    }

    public BranchRes() {
    }

    public Object getRes() {
        return res;
    }

    public Object getQueryRes() {
        return queryRes;
    }

    public void setQueryRes(Object queryRes) {
        this.queryRes = queryRes;
    }

    @Override
    public String toString() {
        return "BranchRes{" +
                "res=" + res +
                ", queryRes=" + queryRes +
                '}';
    }

    public static BranchResBuilder builder() {
        return new BranchResBuilder();
    }

    public static class BranchResBuilder {
        private Object res;
        private Object queryRes;

        BranchResBuilder() {
        }

        public BranchResBuilder res(final Object res) {
            this.res = res;
            return this;
        }

        public BranchResBuilder queryRes(final Object queryRes) {
            this.queryRes = queryRes;
            return this;
        }

        public BranchRes build() {
            return new BranchRes(this.res, this.queryRes);
        }

        public String toString() {
            return "BranchRes.BranchResBuilder(res=" + this.res + ", queryRes=" + this.queryRes + ")";
        }
    }
}
