/**
 * 
 */
package com.afour.emgmt.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	
	/**
	 * This Bean will invoke the docker to build Json and populate i to the swagger-ui.
	 */
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()));
	}
	
	ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Event management APIs")
                .version("1.0.0")
                .description("These are basic endpoints to manage the Events their sessions, Organizers and Visitors")
                .build();
    }
	
	private ApiKey apiKey() {
	    return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
	    return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {authorizationScope};
	    return List.of(new SecurityReference("JWT", authorizationScopes));
	}

}
