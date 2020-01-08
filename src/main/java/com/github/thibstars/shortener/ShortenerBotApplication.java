package com.github.thibstars.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Spring boot application entry point.
 *
 * @author Thibault Helsmoortel
 */
@EnableConfigurationProperties
@SpringBootApplication
public class ShortenerBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortenerBotApplication.class, args);
	}

}
