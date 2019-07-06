package com.training.mjunction.account.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class RestClientConfiguration {

	@Bean
	public RestTemplate restTemplate() {

		final TrustStrategy acceptingTrustStrategy = (final X509Certificate[] chain, final String authType) -> true;

		try {

			final SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy).build();

			final SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			final CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

			final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);

			final RestTemplate restTemplate = new RestTemplate(requestFactory);

			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

			final ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.registerModule(new Jackson2HalModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

			final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

			converter.setObjectMapper(objectMapper);

			restTemplate.getMessageConverters().add(0, converter);

			return restTemplate;

		} catch (final KeyManagementException e) {
			log.error(() -> "KeyManagementException:: ", e);
		} catch (final NoSuchAlgorithmException e) {
			log.error(() -> "NoSuchAlgorithmException:: ", e);
		} catch (final KeyStoreException e) {
			log.error(() -> "KeyStoreException:: ", e);
		}
		return null;
	}

}
