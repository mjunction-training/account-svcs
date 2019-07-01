package com.training.mjunction.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import brave.sampler.Sampler;
import lombok.extern.log4j.Log4j2;

@Log4j2
@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
@EnableOAuth2Client
@EnableMongoRepositories(basePackages = { "com.training.mjunction.account.repo" })
@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		log.info("Starting application user-svcs");
		return application.sources(Application.class);
	}

	public static void main(final String[] args) {
		log.info("Starting application user-svcs");
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public WebMvcConfigurer CORSConfig() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry.addMapping("/api/v1/**");
			}
		};
	}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

}
