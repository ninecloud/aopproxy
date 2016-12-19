package com.cloud.aopproxy;

import net.rubyeye.xmemcached.MemcachedClient;

import javax.annotation.Resource;

/**
 * Created by jiuyun.zhang on 2016/12/16.
 */
public class AopProxy {

    @Resource
    private MemcachedClient memcachedClient;

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}
