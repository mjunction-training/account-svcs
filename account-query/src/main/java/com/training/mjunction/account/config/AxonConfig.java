package com.training.mjunction.account.config;

import org.axonframework.boot.autoconfig.AxonAutoConfiguration;
import org.axonframework.eventhandling.SimpleEventBus;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(AxonAutoConfiguration.class)
public class AxonConfig {

	@Bean
	public SimpleEventBus accountsQueueMessageSource() {
		return new SimpleEventBus();
	}

}
