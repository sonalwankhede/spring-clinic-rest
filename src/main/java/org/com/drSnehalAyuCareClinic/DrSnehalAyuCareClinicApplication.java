package org.com.drSnehalAyuCareClinic;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
public class DrSnehalAyuCareClinicApplication extends SpringBootServletInitializer {
	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}
	public static void main(String[] args) {
		SpringApplication.run(DrSnehalAyuCareClinicApplication.class, args);
	}

}
