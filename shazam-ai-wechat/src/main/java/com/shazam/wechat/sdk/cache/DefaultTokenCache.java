package com.shazam.wechat.sdk.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认 Token 缓存实现（基于内存）
 */
public class DefaultTokenCache implements TokenCache {

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    @Override
    public void put(String key, String token, long expireTime) {
        cache.put(key, new CacheEntry(token, expireTime));
    }

    @Override
    public String get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null) {
            return null;
        }
        
        // 检查是否过期
        if (System.currentTimeMillis() >= entry.expireTime) {
            cache.remove(key);
            return null;
        }
        
        return entry.token;
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * 缓存条目
     */
    private static class CacheEntry {
        private final String token;
        private final long expireTime;

        CacheEntry(String token, long expireTime) {
            this.token = token;
            this.expireTime = expireTime;
        }
    }
}
