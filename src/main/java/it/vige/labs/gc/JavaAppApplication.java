package it.vige.labs.gc;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class JavaAppApplication {

	public final static String BROKER_NAME = "/votingpaper-websocket";
	public final static String TOPIC_NAME = "/topic/votingpaper";

	public static void main(String[] args) {
		run(JavaAppApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	    return builder.build();
	}
}
