package br.edu.ifpb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
  basePackageClasses = { ProsecontrasApplication.class, Jsr310JpaConverters.class }
)

@SpringBootApplication
public class ProsecontrasApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProsecontrasApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProsecontrasApplication.class);
	}

}
