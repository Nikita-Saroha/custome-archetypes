package ${package}.controller.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.any())
        		.paths(PathSelectors.regex("/v${app-version}.*"))
        		.build()
        		.useDefaultResponseMessages(false)
        		.apiInfo(apiInfo());
    }
	
	private ApiInfo apiInfo() {
	    ApiInfo apiInfo = new ApiInfo(
	      "${api-title}",
	      "${api-description}",
	      null,
	      null,
	      null,
	      null,
	      null,
	      new ArrayList<>());
	    return apiInfo;
	}
	
	@Component
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public class DefaultOperationReader implements OperationBuilderPlugin {

		@Override
		public boolean supports(DocumentationType delimiter) {
			return true;
		}

		@Override
		public void apply(OperationContext context) {
			 if(context.requestMappingPattern().equalsIgnoreCase("/v1/names") && context.httpMethod() == HttpMethod.GET){
				 context.operationBuilder().summary("View a list of available names.");
				 context.operationBuilder().notes("getnames - place your notes here");
			 }
			 if(context.requestMappingPattern().equalsIgnoreCase("/v1/names") && context.httpMethod() == HttpMethod.POST){
				 context.operationBuilder().summary("Add a new name with an ID.");
				 context.operationBuilder().notes("setName - place your notes here");
			 }
			 if(context.requestMappingPattern().equalsIgnoreCase("/v1/names/{id}") && context.httpMethod() == HttpMethod.GET){
				 context.operationBuilder().summary("Search a name with an ID.");
				 context.operationBuilder().notes("getNameById - place your notes here");
			 }
			 if(context.requestMappingPattern().equalsIgnoreCase("/v1/names/{id}") && context.httpMethod() == HttpMethod.DELETE){
				 context.operationBuilder().summary("Delete a name from database.");
				 context.operationBuilder().notes("deleteNameById - place your notes here");
			 }
			 if(context.requestMappingPattern().equalsIgnoreCase("/v1/cache/remove/all") && context.httpMethod() == HttpMethod.DELETE){
				 context.operationBuilder().summary("Clear cache.");
				 context.operationBuilder().notes("clearAllCache - place your notes here");
			 }
			 if(context.requestMappingPattern().equalsIgnoreCase("/v1/cache/remove/{id}") && context.httpMethod() == HttpMethod.DELETE){
				 context.operationBuilder().summary("Remove data for ID from cache.");
				 context.operationBuilder().notes("clearCache - place your notes here");
			 }
			
		}
	}

}
