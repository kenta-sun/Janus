# Janus框架简介

Janus框架为<font color=#FF0000 >分流比对框架</font>，主要用于项目重构场景。

重构项目时需要重构一个个具体的java方法。该框架可以从下面3个方面辅助重构到上线的流程：

1. 强制要求以接口的方式统一新旧方法的定义，方便调用、切换新老方法或者进行新老方法之间的比对。
2. Janus框架的分流功能，可以方便开发者从多个维度灵活判断、切换新老方法。生产环境发现新方法有bug时，该功能在还可以帮助开发者方便的切换回老方法。具体的判断、切换逻辑需要开发者根据业务场景自己实现（通过自定义[插件](#jump-target-plugin)）。
3. Janus框架的比对功能，可以方便开发者在生产环境并行新旧方法，并对新旧方法的运行结果进行异步比对。有多种模式供开发者选择，以适配非事务方法和事务方法。如果比对事务方法（方法内有修改数据库的逻辑），需要通过自定义[插件](#jump-target-plugin)来查询方法内修改过的数据（包括对数据库的增删改），否则框架无法对修改的数据进行比对。

## Janus框架实现思路

### 基于 Spring AOP

该框架基于Spring框架的AOP功能实现。在切面中决定返回新方法的结果还是旧方法的结果，并比对他们的结果是否一致。这样做对代码的入侵性较低，并切可以方便的控制新旧方法的切换、记录比对发现的差异。

### 分流比对过程的生命周期

将分流、比对的整个流程中，关键的步骤抽象成生命周期，用装饰模式实现功能增强。

详情见[Janus框架生命周期](#jump-target-lifecycle)。

### 基于生命周期增强实现的插件功能

生命周期不对框架的使用者开放，仅用于框架作者自己实现Janus框架的一些核心功能。

基于生命周期实现的[插件](#jump-target-plugin)功能，可以允许框架使用者来自定义插件，灵活的添加各种功能。

### 部分关键的全局功能允许用户自定义

[部分全局功能允许开发者自定义](#jump-target-custom)。这些功能都提供了接口，并且默认的Bean都设置了`@ConditionalOnMissingBean`，允许用户根据需要自己定义这些功能。

基于约定优于配置的原则，一般情况下使用Janus框架的默认实现即可。


# 主要功能

## 接口定义

采用Janus框架，首先必须用接口统一新旧方法的格式，示例如下。

将要重构的方法定义在接口中：

```java
public interface TestInterface {

    TestResponse testMethod(TestRequest request);
}
```

实现`TestInterface`接口，将新方法（重构后的方法）作为**primary**分支，并添加`@Janus`注解。

<font color=#FF0000 >【注意】</font> 新方法所在的**Service**必须添加Spring框架的`@Primary`注解。这样在注入`TestInterface`接口时才能自动注入`PrimaryService`的Bean。

```java
@Primary
@Service
public class PrimaryService implements TestInterface {

    @Janus(
            methodId = "testMethod",
            compareType = CompareType.ASYNC_COMPARE,
            businessKey = "#request.key"
    )
    @Override
    public TestResponse testMethod(TestRequest request) {
        // ......
    }
}
```

实现`TestInterface`接口，将旧方法作为**secondary**分支。

<font color=#FF0000 >【注意】</font> 旧方法所在的**Service**必须添加Janus框架的`@Secondary`注解。这样Janus框架才能找到**secondary**分支。

```java
@Secondary
@Service
public class SecondaryService implements TestInterface {

    @Override
    public TestResponse testMethod(TestRequest request) {
        // ......
    }
}
```


## 分流

按照上面的接口定义方式，Janus框架必须决定返回哪个分支的结果。

开发者可以通过自定义[插件](#jump-target-plugin)，自己实现插件的`switchBranch`方法，在该方法中设置`JanusContext`的`masterBranchName`属性，即可自定义分流规则。示例如下：

```java
@Component
public class SwitchJanusPlugin implements JanusPlugin {

    @Override
    public void switchBranch(JanusContext context) {
        // ......
        context.setMasterBranchName(JanusConstants.PRIMARY);
        // 或者
        context.setMasterBranchName(JanusConstants.SECONDARY);
    }
}
```

### 允许自定义所有方法默认返回的分支

如果未通过[插件](#jump-target-plugin)来自定义分流规则，默认返回**secondary**分支的结果。

该规则可以通过配置文件修改为**primary**。

```yml
janus:
  # 未配置具体分流开关时默认使用哪个分支。默认：secondary
  # 可选值：primary (新分支), secondary (老分支)
  default-master-branch: primary
```


## 比对

### 比对模式简介

可以通过`@Janus`注解的`compareType`来选择比对模式：

- **DO_NOT_COMPARE**: 只执行主分支，不比对
- **SYNC_COMPARE**: 同步执行2个分支，然后比对
- **ASYNC_COMPARE:** 异步执行比对分支，然后比对
- **SYNC_ROLLBACK_ONE_COMPARE**: 同步执行2个分支，回滚比对分支的事务，然后比对
- **SYNC_ROLLBACK_ALL_COMPARE**: 同步执行2个分支，回滚2个分支的事务，然后比对

### 比对模式选择

1. 非事务接口建议选择`ASYNC_COMPARE`模式。
2. 事务接口根据需要选择`SYNC_ROLLBACK_ONE_COMPARE`或者`SYNC_ROLLBACK_ALL_COMPARE`模式。
   - 选择`SYNC_ROLLBACK_ALL_COMPARE`模式时，被比对的事务方法不会对数据库有任何修改，不可以用于正式上线生产环境的方法。
   - `SYNC_ROLLBACK_ONE_COMPARE`模式可以用于正式上线生产环境的事务方法，但是要注意选对正确的主分支`masterBranch`。
3. `SYNC_COMPARE`模式一般用不到，如果特殊情况下需要可以使用。
4. 如果想关闭比对功能，选择`DO_NOT_COMPARE`模式。

```java
@Janus(
        methodId = "testMethod",
        compareType = CompareType.ASYNC_COMPARE,
        businessKey = "#request.key"
)
```

如果未指定`compareType`，默认为`ASYNC_COMPARE`。也可以通过配置文件修改默认选项。

```yml
janus:
  # 默认比对类型。默认：ASYNC_COMPARE (异步比对)
  default-compare-type: SYNC_COMPARE
```

### 唯一键

#### 方法唯一ID `methodId`

`@Janus`注解的`methodId`是方法级别的唯一ID，代表该方法，必须添加且在项目中全局不允许重复。

校验所有`methodId`是否重复的功能，是默认开启的。如果想关闭可以使用如下配置项：

```yml
janus:
  is-method-id-duplicate-check: false
```

#### 每次比对的唯一键

`@Janus`注解的`businessKey`是每次比对的唯一键，需要自己定义。不设置默认为空。

建议添加该唯一键，用于记录比对日志时区分是哪次调用的日志。

该唯一键的配置支持`SpEL`表达式。`SpEL`表达式不但支持从入参中获取数据，也支持调用静态Java方法。

### 示例

```java
@Janus(
        methodId = "testMethod",
        compareType = CompareType.ASYNC_COMPARE,
        businessKey = "buildKey(#request.key, 'qqq')"
)
@Override
public TestResponse testMethod(TestRequest request) {
    // ......
}
```

### <font color=#FF0000 >重要</font>：MyBatis一级缓存清理

比对事务方法的场景，需要实现对持久层框架的缓存清理，防止因为事务回滚导致的缓存与数据库数据不一致。

比如MyBatis框架存在一级缓存，一级缓存与session绑定。事务结束前，session不会中断，需要在回滚事务后清理一级缓存。

选择`SYNC_ROLLBACK_ONE_COMPARE`或者`SYNC_ROLLBACK_ALL_COMPARE`模式时，强制实现`JanusRollbackClearCache`接口。比如MyBatis框架的项目中需要注入下面的Bean。

```java
@Component
public class JanusRollbackClearCacheImpl implements JanusRollbackClearCache {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void clearCache() {
        sqlSessionTemplate.clearCache();
    }
}
```

### 自定义执行比对分支的线程池<span id="jump-target-janusBranchThreadPool">`janusBranchThreadPool`</span>

该线程用于`ASYNC_COMPARE`模式下，异步执行比对分支。

该线程池可能会有很大压力，因为异步执行比对分支会很耗时。需要根据系统情况自己调整线程数。

不建议修改该线程池的拒绝策略。

可以自己注入该线程池，名字一样（必须使用`janusBranchThreadPool`这个名字）即可替换框架提供的线程池。

也可以通过配置项来自定义框架提供的线程池的配置：

```yml
janus:
  janus-branch-thread-pool:
    # 核心线程数
    core-pool-size: 10
    # 最大线程数
    maximum-pool-size: 20
    # 线程存活时间
    keep-alive-time: 60
    # 时间单位
    # 可选值: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS
    unit: SECONDS
    # 队列大小
    work-queue-size: 1000
    # 拒绝策略
    # 可选值: CallerRunsPolicy, AbortPolicy, DiscardPolicy, DiscardOldestPolicy
    rejected-handler: CallerRunsPolicy
```

### 自定义异步比对线程池<span id="jump-target-janusCompareThreadPool">`janusCompareThreadPool`</span>

该线程池用于异步执行比对2个分支的结果的逻辑。

一般情况下，该线程池的压力不大，因为比对过程消耗资源较少。

不建议修改该线程池的拒绝策略。

可以自己注入该线程池，名字一样（必须使用`janusCompareThreadPool`这个名字）即可替换框架提供的线程池。

也可以通过配置项来自定义框架提供的线程池的配置：

```yml
janus:
  janus-compare-thread-pool:
    # 核心线程数
    core-pool-size: 5
    # 最大线程数
    maximum-pool-size: 10
    # 线程存活时间
    keep-alive-time: 30
    # 时间单位 (可选值: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS)
    unit: SECONDS
    # 队列大小
    work-queue-size: 500
    # 拒绝策略 (可选值: CallerRunsPolicy, AbortPolicy, DiscardPolicy, DiscardOldestPolicy)
    rejected-handler: AbortPolicy
```

### 支持在比对时忽略指定字段

如下面示例，需要通过`@Janus`注解的`ignoreFieldPaths`配置要忽略的字段的路径。必须以`res.`开头，`res`即代表目标方法的返回对象。

```java
@Janus(
        methodId = "testMethod",
        businessKey = "#request.key",
        compareType = CompareType.ASYNC_COMPARE,
        ignoreFieldPaths = {"res.str1", "res.list.str2"}
)
@Override
public TestResponse testMethod(TestRequest request) {
    // ......
}
```

`TestResponse`内部结构：

```java
public class TestResponse {
    private Integer number;
    private String str1;
    private List<TestDTO> list;
}
public class TestDTO {
    private String str2;
    private String str3;
}
```

### 异步比对场景中，自动平衡每个方法的流量

执行比对分支`compareBranch`的线程池`janusBranchThreadPool`的队列被占用到一定比例时，会触发流量自动平衡功能，限制当前流量高的方法的比对流量(不影响它们的主分支的运行，仅丢弃比对任务)。

相关配置项如下：

```yml
janus:
  async-compare-throttling:
    limit-ratio: 0.5 # 默认为0.8，表示线程池的队列被占用了 80% 才会触发流量自动平衡
    isOpen: false # 默认为true，想关闭该功能可以设置为false
```

## Janus生命周期与插件

### <span id="jump-target-lifecycle">Janus框架生命周期</span>

Janus框架的整个分流比对过程，分为4个生命周期：

1. 分流：switchBranch
2. 执行`primary`分支：primaryExecute
3. 执行`secondary`分支：secondaryExecute
4. 比对：compare

### <span id="jump-target-plugin">插件</span>

根据Janus框架4个生命周，可以添加插件来实现各种自定义的扩展功能，比如日志记录等。

支持全局/方法级插件，支持设置插件高低优先级来自定义插件执行顺序，支持插件之间数据的互相访问。

### 插件生命周期

插件分为7个可实现方法：

1. 分流：switchBranch
2. `primary`分支执行前：beforePrimaryExecute
3. `primary`分支执行后：afterPrimaryExecute
4. `secondary`分支执行前：beforeSecondaryExecute
5. `secondary`分支执行后：afterSecondaryExecute
6. 比对前：beforeCompare
7. 比对后：afterCompare

### 新建普通插件

实现`JanusPlugin`接口可以新建插件。注意要将插件注入Spring容器。

比如新建分流插件，用于自定义分流逻辑。

```java
@Component
public class SwitchJanusPlugin implements JanusPlugin {

    @Override
    public void switchBranch(JanusContext context) {
        // ......
        context.setMasterBranchName(......);
    }
}
```

### 新建携带数据的插件

实现`AbstractDataJanusPlugin`抽象类，可以新建携带数据的插件。每一次进入Janus的切面，插件数据都是新建的，同一次比对过程中插件数据是同一个对象、是可传递的。

```java
@Component
public class GetLogJanusPlugin extends AbstractDataJanusPlugin<GetLogJanusPlugin.GetLogJanusPluginData> {

    @Override
    public void afterPrimaryExecute(JanusContext context) {
        // 获取当前插件本次分流比对的相关数据
        GetLogJanusPluginData pluginData = this.getPluginData(context);
        // ......
        pluginData.setLogStr1("......");
    }

    @Override
    public void afterSecondaryExecute(JanusContext context) {
        // 获取当前插件本次分流比对的相关数据，与第一次获取的是同一个对象
        GetLogJanusPluginData pluginData = this.getPluginData(context);
        // ......
        pluginData.setLogStr2("......");
    }

    @Data
    public static class GetLogJanusPluginData {
        private String logStr1;
        private String logStr2;
    }
}
```

### 获取其他插件的数据

```java
@Component
public class SaveLogJanusPlugin implements JanusPlugin {

    @Override
    public void afterCompare(JanusContext context) {
        // 获取 GetLogJanusPlugin 插件本次分流比对的相关数据
        GetLogJanusPlugin.GetLogJanusPluginData getLogJanusPlugin =
                context.getOtherPluginData(GetLogJanusPlugin.class);
        // ......
    }
}
```

### 添加插件到指定方法

```java
@Janus(
        methodId = "testMethod",
        businessKey = "#request.key",
        compareType = CompareType.ASYNC_COMPARE,
        plugins = {GetLogJanusPlugin.class, SaveLogJanusPlugin.class}

)
@Override
public TestResponse testMethod(TestRequest request) {
    // ......
}
```

### 全局插件，`@Global`注解

使用注解可以让插件变为全局插件，不用加在@Janus注解中也能生效。

注意，全局插件每个添加了`@Janus`注解的方法都会生效，无法让某个方法不生效。

```java
@Global
@Component
public class GlobalJanusPlugin implements JanusPlugin {
    // ......
}
```

## <span id="jump-target-custom">允许用户自定义的功能</span>

以下功能都提供了接口，并且框架提供的Bean都设置了`@ConditionalOnMissingBean`，允许用户根据需要自己定义这些功能。

一般情况下使用Janus框架的默认实现即可。

- `JanusCompare`接口：实现该接口可以自己定义比对功能。
- `JanusRollback`接口：实现该接口可以自己定义事务回滚功能。
- `janusBranchThreadPoolMetricsProvider`接口：如果开发者使用了自己定义的线程池，并且线程池的实现不是`ThreadPoolExecutor`类型，则需要开发者自己实现该接口，用来提供线程池的队列的相关数据。该接口有2个抽象方法，`getQueueSize`方法用于获取线程池队列信息当前的size；`getQueueCapacity`方法用户获取线程池队列的最大容量（可以不用很精确）。

Janus框架中用到的2个线程池，都允许使用者自己注入：

- [`janusBranchThreadPool`](#jump-target-janusBranchThreadPool)
- [`janusCompareThreadPool`](#jump-target-janusCompareThreadPool)

也可以使用`JanusThreadPoolComponent`来获取框架提供的线程池，自己做增强操作后再注入Spring。

这样既能增强线程池，又能保留在配置文件中配置线程池属性的效果，并且十分方便。

示例：

```java
@Configuration
public class ThreadPoolConfig {

    @Autowired
    private JanusThreadPoolComponent janusThreadPoolComponent;

    @Bean
    public ExecutorService janusBranchThreadPool() {
        // 创建原始线程池
        ExecutorService janusBranchThreadPool = janusThreadPoolComponent.getJanusBranchThreadPool();
        // 使用 ContextAwareExecutorService 进行增强，确保父线程上下文（如数据源标识）在子线程中可用
        return new ContextAwareExecutorService(janusBranchThreadPool)
                .addContextPropagator(
                        DataSourceContextHolder::getDataSource,
                        DataSourceContextHolder::setDataSource,
                        DataSourceContextHolder::removeDataSource
                );
    }
}
```

## 日志打印

日志打印采用`slf4j`抽象层框架，不提供具体实现。

运行日志级别为`DEBUG`级别。异常日志为`ERROR`级别。

有对插件的每个方法进行运行时间追踪的日志，为`TRACE`级别。
