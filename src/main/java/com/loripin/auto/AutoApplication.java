package com.loripin.auto;

import com.loripin.auto.repos.FileStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

@SpringBootApplication
@EnableAsync
public class AutoApplication implements CommandLineRunner {
	@Resource
	FileStorage fileStorage;

	public static void main(String[] args) {
		SpringApplication.run(AutoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
