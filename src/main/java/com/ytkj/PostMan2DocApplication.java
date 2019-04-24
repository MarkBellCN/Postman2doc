package com.ytkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class PostMan2DocApplication{
	public static void main(String[] args) {
		SpringApplication.run(PostMan2DocApplication.class, args);
	}
}
