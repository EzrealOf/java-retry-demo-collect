package com.ezreal.proxy;

import com.ezreal.util.RetryConstant;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        int times = 0;

        while (times < RetryConstant.MAX_TIMES) {
            try {
                //通过代理子类调用父类的方法
                return methodProxy.invokeSuper(o, objects);
            } catch (Exception e) {
                times++;

                if (times >= RetryConstant.MAX_TIMES) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    public Object getProxy(Class clazz){
        Enhancer enhancer = new Enhancer();
        //目标对象类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //通过字节码技术创建目标对象类的子类实例作为代理
        return enhancer.create();
    }
}
