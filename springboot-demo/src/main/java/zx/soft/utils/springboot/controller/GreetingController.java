package zx.soft.utils.springboot.controller;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zx.soft.utils.springboot.domain.Greeting;
import zx.soft.utils.springboot.service.CustomService;

@RestController
public class GreetingController {

	@Autowired
	private CustomService customService;

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name), new Date());
	}

	@RequestMapping(value = "/return", method = RequestMethod.POST)
	public Greeting returnGreeting(@RequestBody Greeting greeting) {
		return greeting;
	}

	@RequestMapping("/")
	public String home() {
		return customService.home() + " " + customService.hashCode();
	}
}
