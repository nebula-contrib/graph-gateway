//package com.lpz.graph.gateway.web.config;
//
//import com.lpz.graph.gateway.common.util.StringUtil;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//import java.time.Duration;
//import java.util.HashSet;
//import java.util.Set;
//
//
///**
// * @ClassName RedisClusterConfig
// * @Description
// **/
//@Configuration
//public class RedisClusterConfig {
//
//    @Value("${spring.redis.cluster.nodes}")
//    private String nodes;
//    @Value("${spring.redis.password:}")
//    private String password;
//
//    private static final int DEFAULT_CONNECTION_TIMEOUT = 2000;
//    private static final int DEFAULT_SO_TIMEOUT = 2000;
//    private static final int DEFAULT_MAX_ATTEMPTS = 5;
//
//
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;
//
//
//    /**
//     * 基于Java Confing方式定义的JedisCluster
//     *
//     * @return
//     */
//    @Bean
//    public JedisCluster jedisCluster() {
//        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//        //解析集群配置
//        String[] clusterNodes = nodes.split(",");
//        for (String clusterNode : clusterNodes) {
//            String[] node = clusterNode.split(":");
//            String host = node[0];
//            int port = Integer.parseInt(node[1]);
//            jedisClusterNodes.add(new HostAndPort(host, port));
//        }
//        //创建Redis客户端
//        if (StringUtil.isBlank(password)) {
//            return new JedisCluster(jedisClusterNodes);
//        } else {
//            return new JedisCluster(jedisClusterNodes, DEFAULT_CONNECTION_TIMEOUT,
//                    DEFAULT_SO_TIMEOUT, DEFAULT_MAX_ATTEMPTS, password,
//                    new GenericObjectPoolConfig());
//        }
//    }
//
//
//    /**
//     * 实例化 RedisTemplate 对象
//     *
//     * @param redisConnectionFactory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        // 自定义的string序列化器和fastjson序列化器
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // jackson 序列化器
//        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        // kv 序列化
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(jsonRedisSerializer);
////        redisTemplate.setValueSerializer(stringRedisSerializer);
//        // hash 序列化
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
//
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//    /**
//     * @return
//     */
//    @Bean
//    public RedisCacheManager redisCacheManager() {
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
//                .entryTtl(Duration.ofSeconds(3000)); // 默认超时时间;
//        // RedisCacheManager 生成器创建
//        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
//                .cacheDefaults(redisCacheConfiguration).build();
//    }
//
//
//}
//
