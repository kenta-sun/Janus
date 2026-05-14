package io.github.kentasun.janus.core.utils;

import java.lang.invoke.MethodHandle;

/**
 * 反射相关工具
 *
 * @author kenta-sun
 */
public class ReflectUtils {

    /**
     * 调用 {@link MethodHandle} 并返回结果。
     *
     * @param receiver     方法所属对象（实例方法的 receiver），不可为 {@code null}
     * @param methodHandle 目标方法句柄，不可为 {@code null}
     * @param args         透传给目标方法的实参列表（不含 receiver）
     * @return 方法调用的返回值；若方法返回 {@code void} 则为 {@code null}
     */
    public static Object invokeMethodHandle(Object receiver, MethodHandle methodHandle, Object... args) {
        // MethodHandle 首参为 receiver，后续依次为方法实参
        Object[] invokeArgs = new Object[args.length + 1];
        invokeArgs[0] = receiver;
        System.arraycopy(args, 0, invokeArgs, 1, args.length);
        try {
            return methodHandle.invokeWithArguments(invokeArgs);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
