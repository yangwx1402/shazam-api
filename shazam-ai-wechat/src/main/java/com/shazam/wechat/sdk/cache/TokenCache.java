package com.shazam.wechat.sdk.cache;

/**
 * Token 缓存接口
 */
public interface TokenCache {

    /**
     * 缓存 Token
     *
     * @param key        缓存键
     * @param token      Token 值
     * @param expireTime 过期时间（毫秒时间戳）
     */
    void put(String key, String token, long expireTime);

    /**
     * 获取 Token
     *
     * @param key 缓存键
     * @return Token 值，如果不存在或已过期则返回 null
     */
    String get(String key);

    /**
     * 删除 Token
     *
     * @param key 缓存键
     */
    void remove(String key);
}
