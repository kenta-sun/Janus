package com.ethan.janus.core.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class JanusUtils {

    /**
     * 默认值工具
     */
    public static <T> T defaultIfNull(final T object, final T defaultValue) {
        return object != null ? object : defaultValue;
    }

    /**
     * 是否是空字符串
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 字符串是否非空
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * 获取切点方法上面的指定注解
     *
     * @param joinPoint       切点
     * @param annotationClass 注解类
     * @param <T>             注解类型泛型
     * @return 注解对象
     */
    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 获取具体的方法对象
        Method method = signature.getMethod();

        T annotation = AnnotationUtils.findAnnotation(method, annotationClass);
        if (annotation == null) {
            // 获取实现类的具体 Class
            Class<?> targetClass = joinPoint.getTarget().getClass();

            // 找到实现类中对应的方法
            Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);

            // 从具体方法上拿注解
            annotation = AnnotationUtils.findAnnotation(specificMethod, annotationClass);
        }
        return annotation;
    }
}
