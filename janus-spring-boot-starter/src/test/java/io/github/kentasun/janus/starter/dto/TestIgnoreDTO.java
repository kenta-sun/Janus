package io.github.kentasun.janus.starter.dto;

public class TestIgnoreDTO {

    private String str1;

    private String str2;

    public TestIgnoreDTO(String str1, String str2) {
        this.str1 = str1;
        this.str2 = str2;
    }

    public TestIgnoreDTO() {
    }

    public static TestIgnoreDTOBuilder builder() {
        return new TestIgnoreDTOBuilder();
    }

    public String getStr1() {
        return this.str1;
    }

    public String getStr2() {
        return this.str2;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    @Override
    public String toString() {
        return "TestIgnoreDTO{" +
                "str1='" + str1 + '\'' +
                ", str2='" + str2 + '\'' +
                '}';
    }

    public static class TestIgnoreDTOBuilder {
        private String str1;
        private String str2;

        TestIgnoreDTOBuilder() {
        }

        public TestIgnoreDTOBuilder str1(String str1) {
            this.str1 = str1;
            return this;
        }

        public TestIgnoreDTOBuilder str2(String str2) {
            this.str2 = str2;
            return this;
        }

        public TestIgnoreDTO build() {
            return new TestIgnoreDTO(this.str1, this.str2);
        }

        @Override
        public String toString() {
            return "TestIgnoreDTOBuilder{" +
                    "str1='" + str1 + '\'' +
                    ", str2='" + str2 + '\'' +
                    '}';
        }
    }
}
