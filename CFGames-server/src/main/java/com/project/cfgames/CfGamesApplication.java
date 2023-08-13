package com.project.cfgames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CfGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfGamesApplication.class, args);
	}
}
