package io.github.kentasun.janus.starter.dto;

public class TestRequest {

    private String key;

    public TestRequest(String key) {
        this.key = key;
    }

    public TestRequest() {
    }

    public static TestRequestBuilder builder() {
        return new TestRequestBuilder();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TestRequest{" +
                "key='" + key + '\'' +
                '}';
    }

    public static class TestRequestBuilder {
        private String key;

        TestRequestBuilder() {
        }

        public TestRequestBuilder key(String key) {
            this.key = key;
            return this;
        }

        public TestRequest build() {
            return new TestRequest(this.key);
        }

        public String toString() {
            return "TestRequest.TestRequestBuilder(key=" + this.key + ")";
        }
    }
}
