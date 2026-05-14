package io.github.kentasun.janus.core.dto;

import java.lang.invoke.MethodHandle;

/**
 * 分流比对框架 次要方法信息
 */
public class SecondaryMethodInfo {
    // 目标对象
    private Object secondaryService;
    // 目标方法
    private MethodHandle secondaryMethod;

    public SecondaryMethodInfo(final Object secondaryService, final MethodHandle secondaryMethod) {
        this.secondaryService = secondaryService;
        this.secondaryMethod = secondaryMethod;
    }

    public SecondaryMethodInfo() {
    }

    public Object getSecondaryService() {
        return this.secondaryService;
    }

    public MethodHandle getSecondaryMethod() {
        return this.secondaryMethod;
    }

    public void setSecondaryService(final Object secondaryService) {
        this.secondaryService = secondaryService;
    }

    public void setSecondaryMethod(final MethodHandle secondaryMethod) {
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
        private MethodHandle secondaryMethod;

        SecondaryMethodInfoBuilder() {
        }

        public SecondaryMethodInfoBuilder secondaryService(final Object secondaryService) {
            this.secondaryService = secondaryService;
            return this;
        }

        public SecondaryMethodInfoBuilder secondaryMethod(final MethodHandle secondaryMethod) {
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
