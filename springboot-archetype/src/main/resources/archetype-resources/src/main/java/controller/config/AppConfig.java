package ${package}.controller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ${package}.interceptors.LoggingInterceptor;

@Configuration  
public class AppConfig extends WebMvcConfigurerAdapter  {  
	
	@Autowired
	LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(loggingInterceptor);
    }
}