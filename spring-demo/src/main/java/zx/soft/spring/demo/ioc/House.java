package zx.soft.spring.demo.ioc;

public class House {

	private People owner;

	public House() {
		System.out.println("House 构造器");
	}

	public People getOwner() {
		return owner;
	}

	public void setOwner(People owner) {
		this.owner = owner;
	}
}
