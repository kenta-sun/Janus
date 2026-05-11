package io.github.kentasun.janus.core.dto;

import io.github.kentasun.janus.core.compare.JanusCompare;
import io.github.kentasun.janus.core.exception.JanusException;
import io.github.kentasun.janus.core.lifecycle.Lifecycle;
import io.github.kentasun.janus.core.plugin.AbstractDataJanusPlugin;
import io.github.kentasun.janus.core.plugin.JanusPlugin;
import io.github.kentasun.janus.core.utils.JanusLogUtils;
import io.github.kentasun.janus.core.utils.JanusUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Janus 上下文
 */
public class JanusContextImpl implements JanusContext {

    private static final Logger log = LoggerFactory.getLogger(JanusContextImpl.class);

    // 切点对象
    private ProceedingJoinPoint joinPoint;

    // 切点方法
    private Method method;

    // 生命周期实现
    private Lifecycle lifecycle;

    // 用于异步比对两个分支的运行结果的线程池
    private ExecutorService janusCompareThreadPool;

    // 优先级小于0的插件
    private List<JanusPlugin> higherPluginList;

    // 优先级大于0的插件
    private List<JanusPlugin> lowerPluginList;

    // 比对功能实现
    private JanusCompare janusCompare;

    // 唯一标识
    private String methodId;

    // 业务数据键
    private String businessKey;

    // 比对类型
    private String compareType;

    // 是否比对，允许通过插件在运行时判断是否要比对
    private Boolean needCompare;

    // 比对分支运行完后，比对2个分支的结果的过程是否异步执行。默认是异步执行
    private Boolean isAsyncCompare;

    // 主分支名，只允许设置1次，不能随意修改该属性
    private String masterBranchName;

    // 加了 Janus 注解的分支
    private BranchInfoImpl primaryBranch;
    // 次要分支
    private BranchInfoImpl secondaryBranch;

    // 主分支
    private BranchInfoImpl masterBranch;
    // 用于比对的分支
    private BranchInfoImpl compareBranch;

    // 比对结果
    private CompareRes compareRes;

    // 自定义数据
    private Map<Class<?>, Object> pluginDataMap;

    // 比对时忽略的字段路径列表
    private Set<String> ignoreFieldPaths;

    private Long primaryTime;

    private Long secondaryTime;

    public JanusContextImpl(final ProceedingJoinPoint joinPoint, final Method method, final Lifecycle lifecycle, final ExecutorService janusCompareThreadPool, final List<JanusPlugin> higherPluginList, final List<JanusPlugin> lowerPluginList, final JanusCompare janusCompare, final String methodId, final String businessKey, final String compareType, final Boolean needCompare, final Boolean isAsyncCompare, final String masterBranchName, final BranchInfoImpl primaryBranch, final BranchInfoImpl secondaryBranch, final BranchInfoImpl masterBranch, final BranchInfoImpl compareBranch, final CompareRes compareRes, final Map<Class<?>, Object> pluginDataMap, final Set<String> ignoreFieldPaths, final Long primaryTime, final Long secondaryTime) {
        this.joinPoint = joinPoint;
        this.method = method;
        this.lifecycle = lifecycle;
        this.janusCompareThreadPool = janusCompareThreadPool;
        this.higherPluginList = higherPluginList;
        this.lowerPluginList = lowerPluginList;
        this.janusCompare = janusCompare;
        this.methodId = methodId;
        this.businessKey = businessKey;
        this.compareType = compareType;
        this.needCompare = needCompare;
        this.isAsyncCompare = isAsyncCompare;
        this.masterBranchName = masterBranchName;
        this.primaryBranch = primaryBranch;
        this.secondaryBranch = secondaryBranch;
        this.masterBranch = masterBranch;
        this.compareBranch = compareBranch;
        this.compareRes = compareRes;
        this.pluginDataMap = pluginDataMap;
        this.ignoreFieldPaths = ignoreFieldPaths;
        this.primaryTime = primaryTime;
        this.secondaryTime = secondaryTime;
    }

    public JanusContextImpl() {
    }

    @Override
    public Object[] getArgs() {
        return joinPoint.getArgs();
    }

    @Override
    public void setMasterBranchName(String masterBranchName) {
        // 只允许设置1次，不能随意修改该属性
        if (this.masterBranchName == null) {
            this.masterBranchName = masterBranchName;
        } else {
            throw new JanusException("masterBranchName 只能设置1次");
        }
    }

    @Override
    public Boolean needCompare() {
        return this.needCompare;
    }

    @Override
    public Boolean doNotCompare() {
        return !this.needCompare;
    }

    @Override
    public Boolean isAsyncCompare() {
        return isAsyncCompare;
    }

    @Override
    public void setPrimaryQueryRes(Object queryRes) {
        this.primaryBranch.getBranchRes().setQueryRes(queryRes);
    }

    @Override
    public void setSecondaryQueryRes(Object queryRes) {
        this.secondaryBranch.getBranchRes().setQueryRes(queryRes);
    }

    public Object getPluginData(Class<?> clazz) {
        return this.pluginDataMap.get(clazz);
    }

    public void putPluginData(Class<?> clazz, Object data) {
        this.pluginDataMap.put(clazz, data);
    }

    /**
     * 获取切点方法上面的指定注解
     *
     * @param annotationClass 注解类
     * @return 注解对象
     * @param <T> 注解类型泛型
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return JanusUtils.getAnnotation(joinPoint, annotationClass);
    }

    /**
     * 执行主分支
     */
    public void masterBranchExecute() {
        this.executeBranch(this.masterBranch);
    }

    /**
     * 执行比对分支
     */
    public void compareBranchExecute() {
        try {
            this.executeBranch(this.compareBranch);
        } catch (Throwable e) {
            log.error(
                    "[Janus] {} [methodId:{}] [businessKey:{}] [lifecycle:compareBranchExecute] >> exception=",
                    JanusLogUtils.FAIL_ICON,
                    this.methodId,
                    this.businessKey,
                    e
            );
        }
    }

    /**
     * 比对两个分支的结果。
     */
    public void compare() {
        try {
            if (this.isAsyncCompare) {
                janusCompareThreadPool.execute(() -> this.lifecycle.compare(this));
            } else {
                this.lifecycle.compare(this);
            }
        } catch (Throwable e) {
            log.error(
                    "[Janus] {} [methodId:{}] [businessKey:{}] [lifecycle:compare] >> exception=",
                    JanusLogUtils.FAIL_ICON,
                    this.methodId,
                    this.businessKey,
                    e
            );
        }
    }

    /**
     * 执行某个分支
     *
     * @param branch 分支对象
     */
    private void executeBranch(BranchInfoImpl branch) {
        if (branch.getIsExecuted()) {
            // 该分支执行过，不再执行
            return;
        }
        if (branch == this.primaryBranch) {
            // 该分支是 primary 分支
            lifecycle.primaryExecute(this);
        } else {
            // 该分支是 secondary 分支
            lifecycle.secondaryExecute(this);
        }
    }

    public void setPrimaryTime(Long primaryTime) {
        // 只允许设置1次，不能随意修改该属性
        if (this.primaryTime == null) {
            this.primaryTime = primaryTime;
        } else {
            throw new JanusException("primaryTime 只能设置1次");
        }
    }

    public void setSecondaryTime(Long secondaryTime) {
        // 只允许设置1次，不能随意修改该属性
        if (this.secondaryTime == null) {
            this.secondaryTime = secondaryTime;
        } else {
            throw new JanusException("secondaryTime 只能设置1次");
        }
    }

    /**
     * 根据插件类，获取其他插件数据对象
     * <p>如果没找到，会返回null
     *
     * @return 插件数据对象
     */
    public <OTH> OTH getOtherPluginData(Class<? extends AbstractDataJanusPlugin<OTH>> pluginClass) {
        Object pluginDataObj = this.getPluginData(pluginClass);
        if (pluginDataObj != null) {
            //noinspection unchecked
            return (OTH) pluginDataObj;
        } else {
            return null;
        }
    }

    @Override
    public Long getSecondaryTime() {
        return secondaryTime;
    }

    @Override
    public Long getPrimaryTime() {
        return primaryTime;
    }

    @Override
    public Set<String> getIgnoreFieldPaths() {
        return ignoreFieldPaths;
    }

    public Map<Class<?>, Object> getPluginDataMap() {
        return pluginDataMap;
    }

    @Override
    public CompareRes getCompareRes() {
        return compareRes;
    }

    @Override
    public BranchInfoImpl getCompareBranch() {
        return compareBranch;
    }

    @Override
    public BranchInfoImpl getMasterBranch() {
        return masterBranch;
    }

    @Override
    public BranchInfoImpl getSecondaryBranch() {
        return secondaryBranch;
    }

    @Override
    public BranchInfoImpl getPrimaryBranch() {
        return primaryBranch;
    }

    @Override
    public String getMasterBranchName() {
        return masterBranchName;
    }

    @Override
    public String getCompareType() {
        return compareType;
    }

    @Override
    public String getBusinessKey() {
        return businessKey;
    }

    @Override
    public String getMethodId() {
        return methodId;
    }

    public JanusCompare getJanusCompare() {
        return janusCompare;
    }

    public List<JanusPlugin> getLowerPluginList() {
        return lowerPluginList;
    }

    public List<JanusPlugin> getHigherPluginList() {
        return higherPluginList;
    }

    public Lifecycle getLifecycle() {
        return lifecycle;
    }

    public Method getMethod() {
        return method;
    }

    public ProceedingJoinPoint getJoinPoint() {
        return joinPoint;
    }

    @Override
    public void setNeedCompare(Boolean needCompare) {
        this.needCompare = needCompare;
    }

    public void setMasterBranch(BranchInfoImpl masterBranch) {
        this.masterBranch = masterBranch;
    }

    public void setCompareBranch(BranchInfoImpl compareBranch) {
        this.compareBranch = compareBranch;
    }

    public void setCompareRes(CompareRes compareRes) {
        this.compareRes = compareRes;
    }

    @Override
    public String toString() {
        return "JanusContextImpl{" +
                "joinPoint=" + joinPoint +
                ", method=" + method +
                ", lifecycle=" + lifecycle +
                ", janusCompareThreadPool=" + janusCompareThreadPool +
                ", higherPluginList=" + higherPluginList +
                ", lowerPluginList=" + lowerPluginList +
                ", janusCompare=" + janusCompare +
                ", methodId='" + methodId + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", compareType='" + compareType + '\'' +
                ", needCompare=" + needCompare +
                ", isAsyncCompare=" + isAsyncCompare +
                ", masterBranchName='" + masterBranchName + '\'' +
                ", primaryBranch=" + primaryBranch +
                ", secondaryBranch=" + secondaryBranch +
                ", masterBranch=" + masterBranch +
                ", compareBranch=" + compareBranch +
                ", compareRes=" + compareRes +
                ", pluginDataMap=" + pluginDataMap +
                ", ignoreFieldPaths=" + ignoreFieldPaths +
                ", primaryTime=" + primaryTime +
                ", secondaryTime=" + secondaryTime +
                '}';
    }

    public static JanusContextImplBuilder builder() {
        return new JanusContextImplBuilder();
    }

    public static class JanusContextImplBuilder {
        private ProceedingJoinPoint joinPoint;
        private Method method;
        private Lifecycle lifecycle;
        private ExecutorService janusCompareThreadPool;
        private List<JanusPlugin> higherPluginList;
        private List<JanusPlugin> lowerPluginList;
        private JanusCompare janusCompare;
        private String methodId;
        private String businessKey;
        private String compareType;
        private Boolean needCompare;
        private Boolean isAsyncCompare;
        private String masterBranchName;
        private BranchInfoImpl primaryBranch;
        private BranchInfoImpl secondaryBranch;
        private BranchInfoImpl masterBranch;
        private BranchInfoImpl compareBranch;
        private CompareRes compareRes;
        private Map<Class<?>, Object> pluginDataMap;
        private Set<String> ignoreFieldPaths;
        private Long primaryTime;
        private Long secondaryTime;

        JanusContextImplBuilder() {
        }

        public JanusContextImplBuilder joinPoint(final ProceedingJoinPoint joinPoint) {
            this.joinPoint = joinPoint;
            return this;
        }

        public JanusContextImplBuilder method(final Method method) {
            this.method = method;
            return this;
        }

        public JanusContextImplBuilder lifecycle(final Lifecycle lifecycle) {
            this.lifecycle = lifecycle;
            return this;
        }

        public JanusContextImplBuilder janusCompareThreadPool(final ExecutorService janusCompareThreadPool) {
            this.janusCompareThreadPool = janusCompareThreadPool;
            return this;
        }

        public JanusContextImplBuilder higherPluginList(final List<JanusPlugin> higherPluginList) {
            this.higherPluginList = higherPluginList;
            return this;
        }

        public JanusContextImplBuilder lowerPluginList(final List<JanusPlugin> lowerPluginList) {
            this.lowerPluginList = lowerPluginList;
            return this;
        }

        public JanusContextImplBuilder janusCompare(final JanusCompare janusCompare) {
            this.janusCompare = janusCompare;
            return this;
        }

        public JanusContextImplBuilder methodId(final String methodId) {
            this.methodId = methodId;
            return this;
        }

        public JanusContextImplBuilder businessKey(final String businessKey) {
            this.businessKey = businessKey;
            return this;
        }

        public JanusContextImplBuilder compareType(final String compareType) {
            this.compareType = compareType;
            return this;
        }

        public JanusContextImplBuilder needCompare(final Boolean needCompare) {
            this.needCompare = needCompare;
            return this;
        }

        public JanusContextImplBuilder isAsyncCompare(final Boolean isAsyncCompare) {
            this.isAsyncCompare = isAsyncCompare;
            return this;
        }

        public JanusContextImplBuilder masterBranchName(final String masterBranchName) {
            this.masterBranchName = masterBranchName;
            return this;
        }

        public JanusContextImplBuilder primaryBranch(final BranchInfoImpl primaryBranch) {
            this.primaryBranch = primaryBranch;
            return this;
        }

        public JanusContextImplBuilder secondaryBranch(final BranchInfoImpl secondaryBranch) {
            this.secondaryBranch = secondaryBranch;
            return this;
        }

        public JanusContextImplBuilder masterBranch(final BranchInfoImpl masterBranch) {
            this.masterBranch = masterBranch;
            return this;
        }

        public JanusContextImplBuilder compareBranch(final BranchInfoImpl compareBranch) {
            this.compareBranch = compareBranch;
            return this;
        }

        public JanusContextImplBuilder compareRes(final CompareRes compareRes) {
            this.compareRes = compareRes;
            return this;
        }

        public JanusContextImplBuilder pluginDataMap(final Map<Class<?>, Object> pluginDataMap) {
            this.pluginDataMap = pluginDataMap;
            return this;
        }

        public JanusContextImplBuilder ignoreFieldPaths(final Set<String> ignoreFieldPaths) {
            this.ignoreFieldPaths = ignoreFieldPaths;
            return this;
        }

        public JanusContextImplBuilder primaryTime(final Long primaryTime) {
            this.primaryTime = primaryTime;
            return this;
        }

        public JanusContextImplBuilder secondaryTime(final Long secondaryTime) {
            this.secondaryTime = secondaryTime;
            return this;
        }

        public JanusContextImpl build() {
            return new JanusContextImpl(this.joinPoint, this.method, this.lifecycle, this.janusCompareThreadPool, this.higherPluginList, this.lowerPluginList, this.janusCompare, this.methodId, this.businessKey, this.compareType, this.needCompare, this.isAsyncCompare, this.masterBranchName, this.primaryBranch, this.secondaryBranch, this.masterBranch, this.compareBranch, this.compareRes, this.pluginDataMap, this.ignoreFieldPaths, this.primaryTime, this.secondaryTime);
        }

        @Override
        public String toString() {
            return "JanusContextImplBuilder{" +
                    "joinPoint=" + joinPoint +
                    ", method=" + method +
                    ", lifecycle=" + lifecycle +
                    ", janusCompareThreadPool=" + janusCompareThreadPool +
                    ", higherPluginList=" + higherPluginList +
                    ", lowerPluginList=" + lowerPluginList +
                    ", janusCompare=" + janusCompare +
                    ", methodId='" + methodId + '\'' +
                    ", businessKey='" + businessKey + '\'' +
                    ", compareType='" + compareType + '\'' +
                    ", needCompare=" + needCompare +
                    ", isAsyncCompare=" + isAsyncCompare +
                    ", masterBranchName='" + masterBranchName + '\'' +
                    ", primaryBranch=" + primaryBranch +
                    ", secondaryBranch=" + secondaryBranch +
                    ", masterBranch=" + masterBranch +
                    ", compareBranch=" + compareBranch +
                    ", compareRes=" + compareRes +
                    ", pluginDataMap=" + pluginDataMap +
                    ", ignoreFieldPaths=" + ignoreFieldPaths +
                    ", primaryTime=" + primaryTime +
                    ", secondaryTime=" + secondaryTime +
                    '}';
        }
    }
}
