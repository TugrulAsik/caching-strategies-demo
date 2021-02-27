package tr.tugrul.cachedemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class RedisConfig {

    public static final int ONE_MIN = 60;
    public static final int TWENTY_SECONDS = 11;

    @Autowired
    private Environment environment;

    @Value("${redis.host:127.0.0.1}")
    private String host;

    @Value("${redis.port:6379}")
    private Integer port;

    @Value("${redis.password:somepassword}")
    private String password;

    public JedisConnectionFactory jedisConnectionFactoryDefault() {
        String hostname = this.host;
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostname, this.port);
        String pass = this.password;
        ArrayList<String> profiles = new ArrayList<>(Arrays.asList(environment.getActiveProfiles()));
        preparePassword(config, pass, profiles);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        List<String> activeProfiles = new ArrayList<>(Arrays.asList(environment.getActiveProfiles()));
        return jedisConnectionFactoryDefault();
    }

    private void preparePassword(RedisStandaloneConfiguration config, String pass, ArrayList<String> profiles) {
        if (profiles == null || profiles.isEmpty() || profiles.contains("dev")) {
            config.setHostName("127.0.0.1");
            config.setPassword(RedisPassword.none());
        } else {
            if ("none".equalsIgnoreCase(pass)) {
                config.setPassword(RedisPassword.none());
            } else {
                config.setPassword(RedisPassword.of(pass));
            }
        }
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(5 * ONE_MIN));
        return cacheConfig;
    }

    @Primary
    @Bean
    public RedisCacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory) {
        RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory)
                .cacheDefaults(cacheConfiguration())
                .build();
        return rcm;
    }


    public RedisCacheConfiguration twentySecondsCacheConfig() {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(TWENTY_SECONDS));
        return cacheConfig;
    }

    @Bean("twentySecondsCache")
    public RedisCacheManager twentySecondsCacheManager(JedisConnectionFactory jedisConnectionFactory) {
        RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory)
                .cacheDefaults(twentySecondsCacheConfig())
                .build();
        return rcm;
    }
}
