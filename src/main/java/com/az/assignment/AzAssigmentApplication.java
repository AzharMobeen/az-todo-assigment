package com.az.assignment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "ToDo APIs Service", version = "1.0",
		description = "It's a simple TODO API where predefined users can manage ToDo lists"))
@SpringBootApplication
public class AzAssigmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzAssigmentApplication.class, args);
	}
}