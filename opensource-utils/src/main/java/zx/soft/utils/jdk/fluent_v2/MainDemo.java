package zx.soft.utils.jdk.fluent_v2;

public class MainDemo {
	public static void main(String[] args) {
		Cake cake = new Cake().prepare().addFlour().addSugar().mix().bake();

		System.out.println();

		Pizza pizza = new Pizza().prepare().addSauce().addCheese().bake();

		System.out.println();

		cake.eat();
		pizza.eat();
	}
}
