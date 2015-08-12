package zx.soft.spring.demo.autowiring;

public class AutoWiringService {
	private AutoWiringDao autowireDao;

	public AutoWiringService(AutoWiringDao autowireDao) {
		System.out.println("AutoWiringService");
		this.autowireDao = autowireDao;
	}


	public void setAutowireDao(AutoWiringDao autowireDao) {
		System.out.println("setAutowireDao");
		this.autowireDao = autowireDao;
	}


	public void say(String str) {
		autowireDao.say(str);
	}

}
