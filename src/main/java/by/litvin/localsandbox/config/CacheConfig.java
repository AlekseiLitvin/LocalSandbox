package by.litvin.localsandbox.config;

import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

public class CacheConfig {

//    @Bean
// TODO enable caching
public RedisCacheConfiguration redisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .disableCachingNullValues();
}
}
