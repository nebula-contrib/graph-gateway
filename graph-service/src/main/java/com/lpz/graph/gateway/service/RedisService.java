//package com.lpz.graph.gateway.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
///**
// * redis操作工具类，string、map，set等
// */
//@Slf4j
//@Service
//public class RedisService {
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public RedisService() {
//    }
//
//    /**
//     * @param key
//     * @param timeout
//     * @return
//     */
//    public boolean expire(String key, long timeout) {
////        return this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS).booleanValue();
//        try {
//            if (timeout > 0) {
//                return this.redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
//            }
//            return false;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return false;
//        }
//    }
//
//    /**
//     * @param key
//     * @return
//     */
//    public long ttl(String key) {
//        return this.redisTemplate.getExpire(key).longValue();
//    }
//
//    /**
//     * 删除缓存
//     *
//     * @param keys
//     */
//    public void delete(String... keys) {
//        String[] var2 = keys;
//        int length = keys.length;
//        for (int i = 0; i < length; ++i) {
//            String key = var2[i];
//            this.delete(key);
//        }
//
//    }
//
//    /**
//     * @param key
//     */
//    public Boolean delete(String key) {
//        if (this.exists(key)) {
//            log.info("redis delete key: [{}]", key);
//            return this.redisTemplate.delete(key);
//        }
//        return true;
//    }
//
//
//    /**
//     * @param key
//     * @return
//     */
//    public boolean exists(String key) {
//        return this.redisTemplate.hasKey(key).booleanValue();
//    }
//
//    /**
//     * @param pattern
//     */
//    public void deletePattern(String pattern) {
//        Set<Serializable> keys = this.redisTemplate.keys(pattern);
//        if (keys.size() > 0) {
//            this.redisTemplate.delete(keys);
//            log.info("delete pattern: {}, keys: {}", pattern, keys);
//        }
//    }
//
//    // ============================String=============================
//
//    /**
//     * 普通缓存放入
//     *
//     * @param key
//     * @param value
//     * @return
//     */
//    public boolean set(String key, Object value) {
//        boolean result = false;
//        try {
//            this.redisTemplate.opsForValue().set(key, value);
//            result = true;
//            log.info("redis set key: [{}]", key);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return result;
//    }
//
//    /**
//     * @param key
//     * @param value
//     * @param expireTime
//     * @return
//     */
//    public boolean set(String key, Object value, Long expireTime) {
//        boolean result = false;
//        try {
//            this.redisTemplate.opsForValue().set(key, value);
//            this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
//            result = true;
//            log.info("redis set expireTime:{}, key: [{}]", expireTime, key);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return result;
//    }
//
//    /**
//     * @param key
//     * @param value
//     * @param expireTime
//     * @param unit
//     * @return
//     */
//    public boolean set(String key, Object value, Long expireTime, TimeUnit unit) {
//        boolean result = false;
//        try {
//            this.redisTemplate.opsForValue().set(key, value, expireTime, unit);
//            result = true;
//            log.info("redis set key: [{}], expireTime:{}", key, expireTime);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//
//        return result;
//    }
//
//
//    /**
//     * @param key
//     * @return
//     */
//    public Object get(String key) {
//        return key == null ? null : redisTemplate.opsForValue().get(key);
//    }
//
//    /**
//     * 递增
//     *
//     * @param key 键
//     * @return
//     * @paramby 要增加几(大于0)
//     */
//    public long incr(String key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递增因子必须大于0");
//        }
//        return redisTemplate.opsForValue().increment(key, delta);
//    }
//
//    /**
//     * 递减
//     *
//     * @param key 键
//     * @return
//     * @paramby 要减少几(小于0)
//     */
//    public long decr(String key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递减因子必须大于0");
//        }
//        return redisTemplate.opsForValue().decrement(key, delta);
//    }
//
//
//    // ===============Map===============
//
//    /**
//     * @param key
//     * @param hashKey
//     * @param value
//     */
//    public void hmSet(String key, Object hashKey, Object value) {
//        this.redisTemplate.opsForHash().put(key, hashKey, value);
//    }
//
//    /**
//     * @param key
//     * @param hashKey
//     * @return
//     */
//    public Object hmGet(String key, Object hashKey) {
//        return this.redisTemplate.opsForHash().get(key, hashKey);
//    }
//
//    /**
//     * 获取hashKey对应的所有键值
//     *
//     * @param key 键
//     * @return 对应的多个键值
//     */
//    public Map<Object, Object> hmget(String key) {
//        return redisTemplate.opsForHash().entries(key);
//    }
//
//
//    /**
//     * @param key
//     * @return
//     */
//    public Set<Object> hmKeys(String key) {
//        return this.redisTemplate.opsForHash().keys(key);
//    }
//
//    /**
//     * @param masterKey
//     * @param hashKey
//     */
//    public Long hmDel(String masterKey, Object... hashKey) {
//        Long delete = this.redisTemplate.opsForHash().delete(masterKey, hashKey);
//        return delete;
//    }
//
//    /**
//     * HashSet
//     *
//     * @param key 键
//     * @param map 对应多个键值
//     * @return true 成功 false 失败
//     */
//    public boolean hmset(String key, Map<String, Object> map) {
//        try {
//            redisTemplate.opsForHash().putAll(key, map);
//            return true;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return false;
//        }
//    }
//
//
//    // ===============================list=================================
//
//    /**
//     * @param k
//     * @return
//     */
//    public Object lPop(String k) {
//        return this.redisTemplate.opsForList().leftPop(k);
//    }
//
//
//    /**
//     * @param k
//     * @param v
//     */
//    public Long lPush(String k, Object v) {
//        return this.redisTemplate.opsForList().rightPush(k, v);
//    }
//
//    /**
//     * @param k
//     * @param l
//     * @param l1
//     * @return
//     */
//    public List<Object> lRange(String k, long l, long l1) {
//        return this.redisTemplate.opsForList().range(k, l, l1);
//    }
//
//    /**
//     * 获取list缓存的长度
//     *
//     * @param key 键
//     * @return
//     */
//    public long lGetListSize(String key) {
//        try {
//            return redisTemplate.opsForList().size(key);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return 0;
//        }
//    }
//
//    /**
//     * 通过索引 获取list中的值
//     *
//     * @param key   键
//     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
//     * @return
//     */
//    public Object lGetIndex(String key, long index) {
//        try {
//            return redisTemplate.opsForList().index(key, index);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return null;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @return
//     * @paramtime 时间(秒)
//     */
//    public boolean lSet(String key, List<Object> value, long time) {
//        try {
//            redisTemplate.opsForList().rightPushAll(key, value);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return false;
//        }
//    }
//
//    /**
//     * 根据索引修改list中的某条数据
//     *
//     * @param key   键
//     * @param index 索引
//     * @param value 值
//     * @return
//     */
//    public boolean lUpdateIndex(String key, long index, Object value) {
//        try {
//            redisTemplate.opsForList().set(key, index, value);
//            return true;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return false;
//        }
//    }
//
//
//    // =================set=========================
//
//    /**
//     * @param key
//     * @param value
//     */
//    public void add(String key, Object value) {
//
//        this.redisTemplate.opsForSet().add(key, new Object[]{value});
//    }
//
//    /**
//     * 将数据放入set缓存
//     *
//     * @param key    键
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public long sSet(String key, Object... values) {
//        try {
//            return redisTemplate.opsForSet().add(key, values);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return 0;
//        }
//    }
//
//    /**
//     * 将set数据放入缓存
//     *
//     * @param key    键
//     * @param time   时间(秒)
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public long sSetAndTime(String key, long time, Object... values) {
//        try {
//            Long count = redisTemplate.opsForSet().add(key, values);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return count;
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return 0;
//        }
//    }
//
//    /**
//     * @param key
//     * @return
//     */
//    public Set<Object> getSMembers(String key) {
//        return this.redisTemplate.opsForSet().members(key);
//    }
//
//    /**
//     * @param key
//     * @return
//     */
//    public Set<Long> getSMembersVideoIds(String key) {
//        return this.redisTemplate.opsForSet().members(key);
//    }
//
//    /**
//     * 根据value从一个set中查询,是否存在
//     *
//     * @param key   键
//     * @param value 值
//     * @return true 存在 false不存在
//     */
//    public boolean sHasKey(String key, Object value) {
//        try {
//            return redisTemplate.opsForSet().isMember(key, value);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return false;
//        }
//    }
//
//
//    // ====================ZSet=======================
//
//    /**
//     * @param key
//     * @param value
//     * @param scoure
//     */
//    public void zAdd(String key, Object value, double scoure) {
//        this.redisTemplate.opsForZSet().add(key, value, scoure);
//    }
//
//    /**
//     * @param key
//     * @param scoure
//     * @param scoure1
//     * @return
//     */
//    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
//        return this.redisTemplate.opsForZSet().rangeByScore(key, scoure, scoure1);
//    }
//
//
//}