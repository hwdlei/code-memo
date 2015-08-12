package zx.soft.spring.demo.ioc;

public class People {

	private String name;

	private int id;

	public People() {
		System.out.println("People 默认构造器");
	}

	public People(String name, int id) {
		System.out.println("People 参数构造器");
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name + "," + id;

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

}
