package io.github.kentasun.janus.core.dto;

import java.lang.reflect.Method;

/**
 * 分流比对框架 次要方法信息
 */
public class SecondaryMethodInfo {
    // 目标对象
    private Object secondaryService;
    // 目标方法
    private Method secondaryMethod;

    public SecondaryMethodInfo(final Object secondaryService, final Method secondaryMethod) {
        this.secondaryService = secondaryService;
        this.secondaryMethod = secondaryMethod;
    }

    public SecondaryMethodInfo() {
    }

    public Object getSecondaryService() {
        return this.secondaryService;
    }

    public Method getSecondaryMethod() {
        return this.secondaryMethod;
    }

    public void setSecondaryService(final Object secondaryService) {
        this.secondaryService = secondaryService;
    }

    public void setSecondaryMethod(final Method secondaryMethod) {
        this.secondaryMethod = secondaryMethod;
    }

    @Override
    public String toString() {
        return "SecondaryMethodInfo{" +
                "secondaryService=" + secondaryService +
                ", secondaryMethod=" + secondaryMethod +
                '}';
    }

    public static SecondaryMethodInfoBuilder builder() {
        return new SecondaryMethodInfoBuilder();
    }

    public static class SecondaryMethodInfoBuilder {
        private Object secondaryService;
        private Method secondaryMethod;

        SecondaryMethodInfoBuilder() {
        }

        public SecondaryMethodInfoBuilder secondaryService(final Object secondaryService) {
            this.secondaryService = secondaryService;
            return this;
        }

        public SecondaryMethodInfoBuilder secondaryMethod(final Method secondaryMethod) {
            this.secondaryMethod = secondaryMethod;
            return this;
        }

        public SecondaryMethodInfo build() {
            return new SecondaryMethodInfo(this.secondaryService, this.secondaryMethod);
        }

        @Override
        public String toString() {
            return "SecondaryMethodInfoBuilder{" +
                    "secondaryService=" + secondaryService +
                    ", secondaryMethod=" + secondaryMethod +
                    '}';
        }
    }
}
