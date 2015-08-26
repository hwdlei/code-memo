package zx.soft.utils.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * a web @Controller so Spring will consider it when handling incoming web requests
 */
//	@RestController
/**
 * This annotation tells Spring Boot to “guess” how you will want to configure Spring,
 * based on the jar dependencies that you have added.
 * Since spring-boot-starter-web added Tomcat and Spring MVC,
 * the auto-configuration will assume that you are developing a web application and setup Spring accordingly.
 */
//	@EnableAutoConfiguration

//	@SpringBootApplication same as @Configuration @EnableAutoConfiguration @ComponentScan

@SpringBootApplication
@PropertySource(value = { "test.properties" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
