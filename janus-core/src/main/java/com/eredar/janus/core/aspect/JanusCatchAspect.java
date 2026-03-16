package com.eredar.janus.core.aspect;

import com.eredar.janus.core.dto.JanusContext;
import com.eredar.janus.core.utils.JanusLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 异常捕获切面，用于防止不重要的异常导致中断比对流程
 * <p>1. 可以防止影响正常功能
 * <p>2. 可以防止插件等附加功能之间互相影响
 */
@Slf4j
@Component
@Aspect
public class JanusCatchAspect {

    // 假设你的接口全路径是 com.example.service.UserService
    // UserService+ 表示匹配 UserService 接口本身及其所有实现类
    @Pointcut("within(com.eredar.janus.core.plugin.JanusPlugin+) && execution(public * *(..))")
    public void allPluginsMethods() {
    }

    @Around("allPluginsMethods()")
    public Object janusAspect(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            // 捕获日志并且打印日志，不可抛出日志导致流程中断
            try {
                // 获取切点方法入参
                Object[] args = joinPoint.getArgs();
                // 获取签名对象
                Signature signature = joinPoint.getSignature();
                // 获取切点类名
                String pluginClassName = signature.getDeclaringTypeName();
                // 获取方法名称
                String methodName = signature.getName();
                if (args != null && args.length > 0) {
                    Object arg = args[0];
                    if (arg instanceof JanusContext) {
                        JanusContext context = (JanusContext) arg;
                        log.error(
                                "[Janus] {} [methodId:{}] [businessKey:{}] [plugin:{}] [methodName:{}] >> exception=",
                                JanusLogUtils.FAIL_ICON,
                                context.getMethodId(),
                                context.getBusinessKey(),
                                pluginClassName,
                                methodName,
                                e
                        );
                    } else {
                        log.error(
                                "[Janus] {} [plugin:{}] [methodName:{}] >> exception=",
                                JanusLogUtils.FAIL_ICON,
                                pluginClassName,
                                methodName,
                                e
                        );
                    }
                } else {
                    log.error(
                            "[Janus] {} [plugin:{}] [methodName:{}] >> exception=",
                            JanusLogUtils.FAIL_ICON,
                            pluginClassName,
                            methodName,
                            e
                    );
                }
            } catch (Throwable e1) {
                log.error(
                        "[Janus] {} >>  plugin error",
                        JanusLogUtils.FAIL_ICON,
                        e
                );
                log.error(
                        "[Janus] {} >> get JanusContext error",
                        JanusLogUtils.FAIL_ICON,
                        e1
                );
            }
        }
        return null;
    }
}
