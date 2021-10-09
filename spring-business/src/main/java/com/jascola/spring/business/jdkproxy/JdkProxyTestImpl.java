package com.jascola.spring.business.jdkproxy;

public class JdkProxyTestImpl implements JdkProxyTest{
    @Override
    public int sayGood() {
        System.out.println("这是原来的方法");
        return  2;
    }
}
