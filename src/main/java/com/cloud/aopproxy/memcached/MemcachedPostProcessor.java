package com.cloud.aopproxy.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by jiuyun.zhang on 2016/12/18.
 */

public class MemcachedPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (isMatch(bean)) {
            AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
            aspectJExpressionPointcut.setExpression("execution(public * net.rubyeye.xmemcached.MemcachedClient.*(..))");

            Advisor advisor = new DefaultPointcutAdvisor(aspectJExpressionPointcut, memcacheInterceptor());

            ProxyFactory memcachedClientProxyFactory = new ProxyFactory(bean);
            memcachedClientProxyFactory.addAdvisor(advisor);
            memcachedClientProxyFactory.setProxyTargetClass(false);

            Object o = memcachedClientProxyFactory.getProxy();
            return o;
        }
        return bean;
    }

    private boolean isMatch(Object bean) {
        if (MemcachedClient.class.isAssignableFrom(bean.getClass())) {
            return true;
        }
        return false;
    }

    public MemcacheInterceptor memcacheInterceptor(){
        return new MemcacheInterceptor();
    }
}
