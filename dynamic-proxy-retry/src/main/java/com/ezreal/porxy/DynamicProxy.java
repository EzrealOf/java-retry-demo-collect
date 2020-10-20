package com.ezreal.porxy;

import com.ezreal.util.RetryConstant;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ezeal
 */
public class DynamicProxy implements InvocationHandler {

    private final Object subject;

    public DynamicProxy(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int times = 0;

        while (times < RetryConstant.MAX_TIMES) {
            try {
                // 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
                return method.invoke(subject, args);
            } catch (Exception e) {
                times++;

                if (times >= RetryConstant.MAX_TIMES) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    public static Object getProxy(Object o) {
        InvocationHandler handler = new DynamicProxy(o);
        return Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                o.getClass().getInterfaces(), handler);


    }
}
