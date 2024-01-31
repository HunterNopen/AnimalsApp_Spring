package com.example.animals_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.example", "com.example.animals_app", "com.example.animals_app"}, scanBasePackageClasses = Config.class)
public class AnimalsAppApplicationFront {

	public static void main(String[] args) {
		SpringApplication.run(AnimalsAppApplicationFront.class, args);
	}

}
