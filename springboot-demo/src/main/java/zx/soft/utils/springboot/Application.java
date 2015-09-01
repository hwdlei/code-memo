package zx.soft.utils.springboot;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import zx.soft.utils.springboot.dao.UserDao;
import zx.soft.utils.springboot.dao.UserPageDao;
import zx.soft.utils.springboot.domain.User;
import zx.soft.utils.springboot.domain.User.Name;

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
public class Application implements CommandLineRunner {

	@Autowired
	UserDao dao;
	@Autowired
	UserPageDao pageDao;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dao.deleteAll();
		Calendar cal = Calendar.getInstance();
		dao.save(new User("d21958@yeah.net", new Name("hwdlei", ""), new Date()));
		cal.set(2015, 8, 31, 23, 58);
		dao.save(new User("123@456", new Name("Jack", "Bauer"), cal.getTime()));
		cal.set(2015, 8, 31, 22, 58);
		dao.save(new User("456@789", new Name("Chloe", "O'Brian"), cal.getTime()));
		cal.set(2015, 8, 31, 21, 58);
		dao.save(new User("sdjk@123", new Name("Kim", "Bauer"), cal.getTime()));
		cal.set(2015, 8, 31, 20, 58);
		dao.save(new User("138@asdk", new Name("David", "Palmer"), cal.getTime()));
		dao.save(new User("adfk@asdkaj", new Name("Michelle", "Dessler"), cal.getTime()));

		System.out.println("Users found with findAll():");
		System.out.println("-------------------------------");
		for (User user : dao.findAll()) {
			System.out.println(user);
		}
		System.out.println();

		// fetch an individual customer by ID
		User customer = dao.findOne(1L);
		System.out.println("Customer found with findOne(1L):");
		System.out.println("--------------------------------");
		System.out.println(customer);
		System.out.println();

		// fetch customers by last name
		System.out.println("Customer found with findByName('Bauer'):");
		System.out.println("--------------------------------------------");
		for (User bauer : dao.findByName(new Name("Kim", "Bauer"))) {
			System.out.println(bauer);
		}
		// fetch customers by last name
		System.out.println("User found with findByName_LastName('Bauer'):");
		for (User bauer : dao.findByName_LastName("Bauer")) {
			System.out.println(bauer);
		}

		Page<User> users = pageDao.findAll(new PageRequest(1, 2));

		for (User cust : users) {
			System.out.println(cust);
		}

		System.out.println(dao.setFixedFirstnameFor("hwdlei", "donglei"));
	}
}
