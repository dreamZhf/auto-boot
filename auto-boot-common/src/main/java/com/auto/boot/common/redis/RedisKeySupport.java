package com.auto.boot.common.redis;

/**
 * redis key
 * @author dream
 */
public abstract class RedisKeySupport {

    protected static final String COMMON_KEY = "%s:%s";

    /**
     * 生成通用的key
     * @param functionKey key值
     * @return 返回生成的key
     */
    public String generateCommonKey(String functionKey) {
        return String.format(COMMON_KEY, getKeyPrefix(), functionKey);
    }

    /**
     * 获取前缀
     * @return 返回前缀
     */
    protected abstract String getKeyPrefix();
}
