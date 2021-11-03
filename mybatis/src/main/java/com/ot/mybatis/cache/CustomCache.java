package com.ot.mybatis.cache;

import org.apache.ibatis.builder.InitializingObject;
import org.apache.ibatis.cache.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义缓存，实现可以使用内存也可以使用第三方缓存 redis
 */
public class CustomCache implements Cache, InitializingObject {
    /**
     * com.ot.mybatis.dao.UserDao xml当中的namespace
     */
    private String id;
    private Map<Object, Object> map = new HashMap<>(1024);

    public CustomCache() {
    }

    public CustomCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        map.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return map.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int getSize() {
        return map.size();
    }

    /**
     * 此方法可以在参数设置完成之后调用
     *
     * @throws Exception
     */
    @Override
    public void initialize() throws Exception {
        System.out.println("init");
    }
}
