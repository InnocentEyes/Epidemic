package com.qzlnode.epidemic.miniprogram.dao.redis;

/**
 * <h2>对redis缓存数据库的常规操作</h2>
 * @version 1.0
 * @author qzlzzz
 * @param <T>
 */
public interface CommonRedis<T> {

    /**
     * <h3>根据key值往redis数据库中取出值</h3>
     * @param object
     * @return
     */
    String[] get(T object);

    /**
     * <h3>向redis数据库中存入key-value值</h3>
     * @param object
     */
    void set(T object);

    /**
     * <h3>删除库中的键值对</h3>
     * @param object
     * @return {@code true} or {@code false}
     */
    default boolean delete(T object){
     return false;
    }


}
