package com.ssafy.enjoytrip.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Bean
	public Docket api() {
		final Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=utf-8");
		consumes.add("application/x-www-form-urlencoded");

		final ApiInfo apiInfo = new ApiInfoBuilder(
		).title(
			"Enjoy Trip API"
		).description(
			"Enjoy Trip API"
		).contact(
			new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com")
		).build();

		return new Docket(
			DocumentationType.SWAGGER_2
		).consumes(
			consumes
		).apiInfo(
			apiInfo
		).groupName(
			"v1"
		).select(
		).apis(
			RequestHandlerSelectors.basePackage("com.ssafy.enjoytrip")
		).paths(
			PathSelectors.regex("\\/(board|member|plan|comment)\\/.*")
		).build(
		).useDefaultResponseMessages(
			false
		);
	}
}
