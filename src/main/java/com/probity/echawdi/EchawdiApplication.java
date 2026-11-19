/**
 * Echawdi App
 *
 */
package com.probity.echawdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ashish Dahale
 *
 */

@SpringBootApplication
@EnableScheduling
public class EchawdiApplication {

	private static final Logger logger = LoggerFactory.getLogger(EchawdiApplication.class);

	public static void main(String[] args)  {
		SpringApplication.run(EchawdiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET","POST","PUT","DELETE", "PATCH");
			}
		};
	}

	public static Logger getLogger() {
		return logger;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}