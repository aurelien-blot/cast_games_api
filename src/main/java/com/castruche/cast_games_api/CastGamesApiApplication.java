package com.castruche.cast_games_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CastGamesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CastGamesApiApplication.class, args);
	}

}
