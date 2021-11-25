package com.senacor.migrationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class MigrationDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MigrationDemoApplication.class, args);
	}

}
