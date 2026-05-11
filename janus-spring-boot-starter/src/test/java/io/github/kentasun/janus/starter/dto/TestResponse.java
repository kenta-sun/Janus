package io.github.kentasun.janus.starter.dto;

import java.util.List;

public class TestResponse {

    private Integer number;

    private String ignoreStr1;

    private List<TestIgnoreDTO> ignoreList;

    public TestResponse(Integer number, String ignoreStr1, List<TestIgnoreDTO> ignoreList) {
        this.number = number;
        this.ignoreStr1 = ignoreStr1;
        this.ignoreList = ignoreList;
    }

    public TestResponse() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getIgnoreStr1() {
        return ignoreStr1;
    }

    public void setIgnoreStr1(String ignoreStr1) {
        this.ignoreStr1 = ignoreStr1;
    }

    public List<TestIgnoreDTO> getIgnoreList() {
        return ignoreList;
    }

    public void setIgnoreList(List<TestIgnoreDTO> ignoreList) {
        this.ignoreList = ignoreList;
    }

    @Override
    public String toString() {
        return "TestResponse{" +
                "number=" + number +
                ", ignoreStr1='" + ignoreStr1 + '\'' +
                ", ignoreList=" + ignoreList +
                '}';
    }

    public static TestResponse.TestResponseBuilder builder() {
        return new TestResponse.TestResponseBuilder();
    }

    public static final class TestResponseBuilder {
        private Integer number;
        private String ignoreStr1;
        private List<TestIgnoreDTO> ignoreList;

        public TestResponseBuilder() {
        }

        public TestResponseBuilder number(Integer number) {
            this.number = number;
            return this;
        }

        public TestResponseBuilder ignoreStr1(String ignoreStr1) {
            this.ignoreStr1 = ignoreStr1;
            return this;
        }

        public TestResponseBuilder ignoreList(List<TestIgnoreDTO> ignoreList) {
            this.ignoreList = ignoreList;
            return this;
        }

        public TestResponse build() {
            return new TestResponse(this.number, this.ignoreStr1, this.ignoreList);
        }
    }
}
