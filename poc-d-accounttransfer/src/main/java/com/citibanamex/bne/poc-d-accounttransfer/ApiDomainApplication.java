package com.citibanamex.bne.poc-d-accounttransfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.citibanamex.bne.poc-d-accounttransfer.errorhandling.exception.MicroserviceClientException;

import feign.codec.ErrorDecoder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com.citibanamex.bne")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class ApiDomainApplication {
	private final Logger logger = LoggerFactory.getLogger(ApiDomainApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApiDomainApplication.class, args);
	}

	/**
	 * Required bean for scanning the existing API's in project and generating the
	 * swagger-ui interface
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).
				 useDefaultResponseMessages(false).select()
				.apis(RequestHandlerSelectors.basePackage("com.citibanamex.bne.poc-d-accounttransfer.api"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("")
				.description("Description service to be defined ...").version("1.0")
				.contact(new Contact("To be defined", "https://www.anzen.com.mx/", "tobedefined@anzen.com.mx")).build();
	}

	/**
	 * Required bean for validating path Variables, Request Params, Query Parameters
	 */
	@Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
         return new MethodValidationPostProcessor();
        }

	/**
	 * Required bean to allow feign client process responses diferent from http status code 200
	 */
	@Bean
	public ErrorDecoder errorDecoder() {
		return (methodKey, response) -> {
			logger.info("errorDecoder.response: {}", response.toString());

			return new MicroserviceClientException();
		};
	}
}
