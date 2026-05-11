package io.github.kentasun.janus.starter.dto;

/**
 * 测试回滚表实体
 */
public class TestRollbackEntity {

    private String tblKey;
    private Integer tblNum;

    public TestRollbackEntity(String tblKey, Integer tblNum) {
        this.tblKey = tblKey;
        this.tblNum = tblNum;
    }

    public TestRollbackEntity() {
    }

    public static TestRollbackEntityBuilder builder() {
        return new TestRollbackEntityBuilder();
    }

    public String getTblKey() {
        return this.tblKey;
    }

    public Integer getTblNum() {
        return this.tblNum;
    }

    public void setTblKey(String tblKey) {
        this.tblKey = tblKey;
    }

    public void setTblNum(Integer tblNum) {
        this.tblNum = tblNum;
    }

    @Override
    public String toString() {
        return "TestRollbackEntity{" +
                "tblKey='" + tblKey + '\'' +
                ", tblNum=" + tblNum +
                '}';
    }

    public static class TestRollbackEntityBuilder {
        private String tblKey;
        private Integer tblNum;

        TestRollbackEntityBuilder() {
        }

        public TestRollbackEntityBuilder tblKey(String tblKey) {
            this.tblKey = tblKey;
            return this;
        }

        public TestRollbackEntityBuilder tblNum(Integer tblNum) {
            this.tblNum = tblNum;
            return this;
        }

        public TestRollbackEntity build() {
            return new TestRollbackEntity(this.tblKey, this.tblNum);
        }

        @Override
        public String toString() {
            return "TestRollbackEntityBuilder{" +
                    "tblKey='" + tblKey + '\'' +
                    ", tblNum=" + tblNum +
                    '}';
        }
    }
}