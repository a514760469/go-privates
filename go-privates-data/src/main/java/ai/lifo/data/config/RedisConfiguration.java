package ai.lifo.data.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhanglifeng
 * @since 2024-06-25
 */
@EnableCaching
@SpringBootConfiguration
public class RedisConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {

        Map<String, CacheConfig> config = new HashMap<>();

        // create "event" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        long ttl = 24 * 60 * 1000;
        long maxIdleTime = 12 * 60 * 1000;

        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(applicationContext::getType).filter(Objects::nonNull)
                .forEach(clazz -> {
                            ReflectionUtils.doWithMethods(clazz, method -> {
                                ReflectionUtils.makeAccessible(method);
                                Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
                                if (Objects.nonNull(cacheable)) {
                                    for (String cache : cacheable.cacheNames()) {
                                        config.put(cache, new CacheConfig(ttl, maxIdleTime));
                                    }
                                }
                            });
                        }
                );

        RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient, config);
        cacheManager.setCodec(new JsonJacksonCodec(objectMapper()));
        return cacheManager;
    }

    /**
     * jackson objectMapper 配置
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
