package com.jascola.spring.business.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkproxyClass {

    private final static Class[] list = {JdkProxyTest.class};

    public void proxy(){

        //ClassLoader,要代理的类的接口，代理类的实例
        JdkProxyTest test =  (JdkProxyTest) Proxy.newProxyInstance(JdkproxyClass.class.getClassLoader(),list, new JdkproxyImpl(new JdkProxyTestImpl()));

        test.sayGood();
    }

    static class JdkproxyImpl implements InvocationHandler{

        private final Object obj;

        JdkproxyImpl(Object obj){
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println(proxy.getClass());

            System.out.println("增强方法pre");

            Object re = method.invoke(obj,args);

            System.out.println("增强方法post");

            return  re;
        }
    }

    public static void main(String[] args) {
        JdkproxyClass jdkproxyClass = new JdkproxyClass();
        jdkproxyClass.proxy();
    }
}
