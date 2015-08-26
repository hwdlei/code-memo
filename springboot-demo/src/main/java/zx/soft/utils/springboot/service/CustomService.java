package zx.soft.utils.springboot.service;

import org.springframework.stereotype.Service;

@Service
public class CustomService {

	public String home() {
		return "hello, world!";
	}
}
