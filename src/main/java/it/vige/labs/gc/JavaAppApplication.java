package it.vige.labs.gc;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaAppApplication {

	public final static String BROKER_NAME = "/votingpaper-websocket";
	public final static String TOPIC_NAME = "/topic/votingpaper";

	public static void main(String[] args) {
		run(JavaAppApplication.class, args);
	}
}
