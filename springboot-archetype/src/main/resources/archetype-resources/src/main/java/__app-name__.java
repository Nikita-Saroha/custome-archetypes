package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@ComponentScan(basePackages = {"com.cox.amp.commons.logging", "${package}.*"})
@EnableDiscoveryClient
@PropertySource({"classpath:${resource-name}.properties"})
public class ${app-name} {
	
    public static void main(String args[]) {
    	SpringApplication.run(${app-name}.class, args);
    }

}