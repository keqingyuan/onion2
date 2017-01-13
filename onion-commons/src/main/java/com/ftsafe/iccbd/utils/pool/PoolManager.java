package com.ftsafe.iccbd.utils.pool;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ft on 2016/11/25.
 */
public class PoolManager<T> {

    private T pool;

    private static PoolManager instance;

    private ConcurrentHashMap<String, T> concurrentHashMap;

    private PoolManager() {
        this.concurrentHashMap = new ConcurrentHashMap<>(1);
    }

    public static PoolManager getInstance() {
        if (instance == null)
            instance = new PoolManager<>();
        return instance;
    }

    public void register(String key, T pool) {
        if (pool == null) return;
        this.concurrentHashMap.putIfAbsent(key, pool);
    }

    public T getPool(String key) {
        return this.concurrentHashMap.get(key);
    }
}
