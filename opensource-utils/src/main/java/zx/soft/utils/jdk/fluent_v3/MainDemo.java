package zx.soft.utils.jdk.fluent_v3;

public class MainDemo {
	public static void main(String[] args) {
		GenericCake<String> cake = new GenericCake<String>().prepare().addFlour().addSugar()
				.addSecretIngredient("Yogurt").mix().bake();

		System.out.println();

		GenericPizza<String> pizza = new GenericPizza<String>().prepare().addSauce().addCheese().addTopping("Bacon")
				.addSecretIngredient("Arugula").addTopping("Clams").bake();

		System.out.println();

		cake.eat();
		pizza.eat();

		Muffin muffin = new Muffin().prepare().addFlour().addEggs().addSecretIngredient(Muffin.Mixin.Blueberries)
				.bake();

		System.out.println();

		muffin.eat();
	}
}
