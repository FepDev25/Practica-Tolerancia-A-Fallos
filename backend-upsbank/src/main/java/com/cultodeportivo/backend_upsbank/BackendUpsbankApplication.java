package com.cultodeportivo.backend_upsbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class BackendUpsbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendUpsbankApplication.class, args);
	}

}
