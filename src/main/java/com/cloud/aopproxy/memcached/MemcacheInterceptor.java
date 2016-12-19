package com.cloud.aopproxy.memcached;

import com.google.common.collect.Lists;
import net.rubyeye.xmemcached.MemcachedClient;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiuyun.zhang on 2016/12/18.
 */
public class MemcacheInterceptor implements MethodInterceptor {
    private static final String MEM_PRE = "mem_";
    private static List<String> memCommandsMethodNames = getMemCommandsMethodNames();

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object o = null;
        try {
            o = invocation.proceed();
        } catch (Throwable t) {
            //
        }
        if (isMethodOfMem(invocation)) {
            System.out.println("===================");
            System.out.println("methodName: " + invocation.getMethod().getName());
            System.out.println("param：" + Arrays.toString(invocation.getArguments()));
            System.out.println("fullName: " + getFullName(invocation));
            System.out.println("retValue: " + o);
            System.out.println("===================");
        }

        return o;
    }

    private static boolean isMethodOfMem(MethodInvocation invocation) {
        String methodName = invocation.getMethod().getName();
        return memCommandsMethodNames.contains(methodName);
    }

    private static List<String> getMemCommandsMethodNames() {
        List<String> methodNames = Lists.newArrayList();
        for (Method method : MemcachedClient.class.getMethods()) {
            methodNames.add(method.getName());
        }

        return methodNames;
    }

    private static String getFullName(MethodInvocation invocation) {
        String methodName = invocation.getMethod().getName();
        String param = Arrays.toString(invocation.getArguments());
        return getFullName(methodName, param);
    }

    private static String getFullName(String methodName, String param) {
        return String.format("%s%s", MEM_PRE, methodName);
    }
}
