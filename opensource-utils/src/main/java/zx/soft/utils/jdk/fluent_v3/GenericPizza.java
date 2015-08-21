package zx.soft.utils.jdk.fluent_v3;

public class GenericPizza<T> extends GenericBakedGood<GenericPizza<T>, T> {

	public GenericPizza<T> addCheese() {
		System.out.println("adding cheese");
		return this;
	}

	public GenericPizza<T> addSauce() {
		System.out.println("adding sauce");
		return this;
	}

	public GenericPizza<T> addTopping(T topping) {
		System.out.println("putting some " + topping + " on top");
		return this;
	}

	@Override
	public GenericPizza<T> bake() {
		System.out.println("baking at 450 degrees");
		return this;
	}
}