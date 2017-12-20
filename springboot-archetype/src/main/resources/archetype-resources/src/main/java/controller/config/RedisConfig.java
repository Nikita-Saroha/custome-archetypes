package ${package}.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {
	

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@ConditionalOnProperty(prefix = "enable.cluster" ,name="caching", havingValue="true")
	@Bean
    @Autowired
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
		RedisClusterConfiguration redisClusterConfig = null;
		redisClusterConfig = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
//		redisClusterConfig.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisClusterConfig);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }
	
	@ConditionalOnProperty(prefix = "enable.cluster" ,name="caching", havingValue="false", matchIfMissing=true)
	@Bean
	JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(redisProperties.getHost());
		factory.setPort(redisProperties.getPort());
		factory.setUsePool(true);
		return factory;
	}
	
	@Bean
	RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory cf) {
		final RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(cf);
		template.setKeySerializer(new GenericToStringSerializer(Integer.class));
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	@Bean
	CacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
		RedisCacheManager manager = new RedisCacheManager(redisTemplate);
		manager.setUsePrefix(true);
		return manager;
	}
}
