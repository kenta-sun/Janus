package io.github.kentasun.janus.core.dto;

/**
 * Janus 切面状态，用于传递到切面外。
 */
public class JanusAspectStatus {

    // 主分支名
    private String masterBranchName;

    public JanusAspectStatus() {
    }

    public JanusAspectStatus(String masterBranchName) {
        this.masterBranchName = masterBranchName;
    }

    public String getMasterBranchName() {
        return masterBranchName;
    }

    public void setMasterBranchName(String masterBranchName) {
        this.masterBranchName = masterBranchName;
    }

    @Override
    public String toString() {
        return "JanusAspectStatus{" +
                "masterBranchName='" + masterBranchName + '\'' +
                '}';
    }

    public static JanusAspectStatusBuilder builder() {
        return new JanusAspectStatusBuilder();
    }

    public static class JanusAspectStatusBuilder {
        private String masterBranchName;

        JanusAspectStatusBuilder() {
        }

        public JanusAspectStatusBuilder masterBranchName(final String masterBranchName) {
            this.masterBranchName = masterBranchName;
            return this;
        }

        public JanusAspectStatus build() {
            return new JanusAspectStatus(this.masterBranchName);
        }

        public String toString() {
            return "JanusAspectStatus.JanusAspectStatusBuilder(masterBranchName=" + this.masterBranchName + ")";
        }
    }
}
