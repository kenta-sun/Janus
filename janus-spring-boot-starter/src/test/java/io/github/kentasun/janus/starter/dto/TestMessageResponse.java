package io.github.kentasun.janus.starter.dto;

public class TestMessageResponse {

    private String flag;

    private String message;

    public TestMessageResponse(String flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public TestMessageResponse() {
    }

    public static TestMessageResponseBuilder builder() {
        return new TestMessageResponseBuilder();
    }

    public String getFlag() {
        return this.flag;
    }

    public String getMessage() {
        return this.message;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "TestMessageResponse(flag=" + this.getFlag() + ", message=" + this.getMessage() + ")";
    }

    public static class TestMessageResponseBuilder {
        private String flag;
        private String message;

        TestMessageResponseBuilder() {
        }

        public TestMessageResponseBuilder flag(String flag) {
            this.flag = flag;
            return this;
        }

        public TestMessageResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public TestMessageResponse build() {
            return new TestMessageResponse(this.flag, this.message);
        }

        @Override
        public String toString() {
            return "TestMessageResponseBuilder{" +
                    "flag='" + flag + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
