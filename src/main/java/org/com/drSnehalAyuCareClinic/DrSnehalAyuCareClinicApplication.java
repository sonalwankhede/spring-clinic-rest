package org.com.drSnehalAyuCareClinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DrSnehalAyuCareClinicApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DrSnehalAyuCareClinicApplication.class, args);
	}
}
