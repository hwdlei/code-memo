package zx.soft.spring.demo.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zx.soft.spring.demo.autowiring.AutoWiringService;


public class MainTest {

	public static void main(String[] args) {
		// IOC
		//		ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/spring-ioc.xml");
		//		House house = (House) context.getBean("house");
		//		System.out.println(house.getOwner().getName());

		// Scope
		//		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-scope.xml");
		//		BeanScope bs = ctx.getBean("beanScope", BeanScope.class);
		//		bs.say();
		//		BeanScope bs2 = ctx.getBean("beanScope", BeanScope.class);
		//		bs2.say();

		// lifecycle
		//		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-lifecycle.xml");
		//		BeanLifecycle bs = ctx.getBean("lifecycle", BeanLifecycle.class);

		// Aware
		//		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-lifecycle.xml");
		//		System.out.println(ctx.getBean("beanAware", BeanAware.class));

		//AutoWiring
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-autowire.xml");
		AutoWiringService aws = (AutoWiringService) ctx.getBean("autowireService");
		aws.say("This is autowiring test");

	}

}
