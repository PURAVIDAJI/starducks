package org.kosta.starducks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.TimeZone;

//@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class StarducksApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
		SpringApplication.run(StarducksApplication.class, args);
	}
}
