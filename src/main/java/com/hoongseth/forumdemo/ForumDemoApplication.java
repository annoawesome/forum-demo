package com.hoongseth.forumdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ForumDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumDemoApplication.class, args);
	}

}
