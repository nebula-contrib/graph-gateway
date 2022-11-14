package com.lpz.graph.gateway.common.util;

import com.lpz.graph.gateway.common.param.resp.SessionBo;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 存放session的cache工具类
 */
@Slf4j
public class CacheManager {


    /**
     * 缓存项最大数量
     */
    private static final long GUAVA_CACHE_SIZE = 100000;

    /**
     * 缓存时间：小时
     */
    private static final long GUAVA_CACHE_HOURS = 12;

    /**
     * 缓存操作对象
     */
    public static LoadingCache<String, SessionBo> SESSION_MAP_CACHE = null;

    static {
        try {
            SESSION_MAP_CACHE = loadCache(new CacheLoader<String, SessionBo>() {
                @Override
                public SessionBo load(String key) throws Exception {
                    // 处理缓存键不存在缓存值时的处理逻辑
                    log.info("cache key: {} not exist..", key);
                    return null;
                }
            });
        } catch (Exception e) {
            log.error("Guava Cache init error...", e);
        }
    }

    /**
     * 全局缓存设置
     * <p>
     * 缓存项最大数量：100000
     * 缓存有效时间（小时）：12
     *
     * @param cacheLoader
     * @return
     * @throws Exception
     */
    private static LoadingCache<String, SessionBo> loadCache(CacheLoader<String, SessionBo> cacheLoader) {
        LoadingCache<String, SessionBo> cache = CacheBuilder.newBuilder()
                //缓存池大小，在缓存项接近该大小时， Guava开始回收旧的缓存项
                .maximumSize(GUAVA_CACHE_SIZE)
                //设置时间对象没有被读/写访问则对象从内存中删除(在另外的线程里面不定期维护)
                .expireAfterAccess(GUAVA_CACHE_HOURS, TimeUnit.HOURS)
                // 设置缓存在写入之后 设定时间 后失效
//                .expireAfterWrite(GUAVA_CACHE_HOURS, TimeUnit.HOURS)
                //移除监听器,缓存项被移除时会触发
                .removalListener(new RemovalListener<String, SessionBo>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, SessionBo> rn) {
                        log.info("cache key: {} is removed...", rn.getKey());
                        // 逻辑操作，清除session
                        rn.getValue().getSession().release();
                    }
                })
                //开启Guava Cache的统计功能
                .recordStats()
                .build(cacheLoader);
        return cache;
    }

    /**
     * 设置缓存值
     * 注: 若已有该key值，则会先移除(会触发removalListener移除监听器)，再添加
     *
     * @param key
     * @param value
     */
    public static void put(String key, SessionBo value) {
        try {
            SESSION_MAP_CACHE.put(key, value);
        } catch (Exception e) {
            log.error("设置缓存值出错", e);
        }
    }

    /**
     * 批量设置缓存值
     *
     * @param map
     */
    public static void putAll(Map<? extends String, ? extends SessionBo> map) {
        try {
            SESSION_MAP_CACHE.putAll(map);
        } catch (Exception e) {
            log.error("批量设置缓存值出错", e);
        }
    }

    /**
     * 获取缓存值
     * 注：如果键不存在值，将调用CacheLoader的load方法加载新值到该键中
     *
     * @param key
     * @return
     */
    public static SessionBo get(String key) {
        SessionBo sessionBo = null;
        try {
            sessionBo = SESSION_MAP_CACHE.getIfPresent(key);
//            sessionBo = SESSION_MAP_CACHE.getUnchecked(key);
//            sessionBo = SESSION_MAP_CACHE.get(key);
        } catch (Exception e) {
            log.error("get cache key error... {}", e.getMessage());
        }
        return sessionBo;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public static void remove(String key) {
        try {
            SESSION_MAP_CACHE.invalidate(key);
        } catch (Exception e) {
            log.error("移除缓存出错", e);
        }
    }

    /**
     * 批量移除缓存
     *
     * @param keys
     */
    public static void removeAll(Iterable<String> keys) {
        try {
            SESSION_MAP_CACHE.invalidateAll(keys);
        } catch (Exception e) {
            log.error("批量移除缓存出错", e);
        }
    }

    /**
     * 清空所有缓存
     */
    public static void removeAll() {
        try {
            SESSION_MAP_CACHE.invalidateAll();
        } catch (Exception e) {
            log.error("清空所有缓存出错", e);
        }
    }

    /**
     * 获取缓存项数量
     *
     * @return
     */
    public static long size() {
        long size = 0;
        try {
            size = SESSION_MAP_CACHE.size();
        } catch (Exception e) {
            log.error("获取缓存项数量出错", e);
        }
        return size;
    }
}