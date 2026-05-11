package io.github.kentasun.janus.core.config;

public class ExpressionRootObject {

    private Object targetBean;

    public ExpressionRootObject(final Object targetBean) {
        this.targetBean = targetBean;
    }

    public ExpressionRootObject() {
    }

    /**
     * 拼接 key
     *
     * @param values 拼接使用的参数
     * @return key 字符串
     */
    public String buildKey(Object... values) {
        if (values == null || values.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object value : values) {
            if (value != null) {
                sb.append(value);
            } else {
                sb.append("#");
            }
            sb.append("_");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public Object getTargetBean() {
        return this.targetBean;
    }

    public void setTargetBean(final Object targetBean) {
        this.targetBean = targetBean;
    }

    @Override
    public String toString() {
        return "ExpressionRootObject{" +
                "targetBean=" + targetBean +
                '}';
    }

    public static ExpressionRootObjectBuilder builder() {
        return new ExpressionRootObjectBuilder();
    }

    public static class ExpressionRootObjectBuilder {
        private Object targetBean;

        ExpressionRootObjectBuilder() {
        }

        public ExpressionRootObjectBuilder targetBean(final Object targetBean) {
            this.targetBean = targetBean;
            return this;
        }

        public ExpressionRootObject build() {
            return new ExpressionRootObject(this.targetBean);
        }

        public String toString() {
            return "ExpressionRootObject.ExpressionRootObjectBuilder(targetBean=" + this.targetBean + ")";
        }
    }
}
