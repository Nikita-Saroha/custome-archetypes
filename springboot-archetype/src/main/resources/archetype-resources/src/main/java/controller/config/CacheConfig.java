package ${package}.controller.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "enable" ,name="caching", havingValue="true", matchIfMissing = true)
@EnableCaching
public class CacheConfig {

}