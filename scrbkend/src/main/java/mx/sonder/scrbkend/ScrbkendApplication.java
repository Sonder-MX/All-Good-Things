package mx.sonder.scrbkend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ScrbkendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScrbkendApplication.class, args);
		log.info("******Application started successfully******");
	}

}
