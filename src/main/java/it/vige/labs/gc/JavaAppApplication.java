package it.vige.labs.gc;

import static java.lang.Long.MAX_VALUE;
import static org.springframework.boot.SpringApplication.run;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class JavaAppApplication {

	public final static String BROKER_NAME = "/votingpaper-websocket";
	public final static String TOPIC_NAME = "/topic/votingpaper";

	public static void main(String[] args) {
		run(JavaAppApplication.class, args);
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(MAX_VALUE);
		return commonsMultipartResolver;
	}

	@Bean
	public KeycloakConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}
}
