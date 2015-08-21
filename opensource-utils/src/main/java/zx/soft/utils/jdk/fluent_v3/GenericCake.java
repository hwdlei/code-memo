package zx.soft.utils.jdk.fluent_v3;

public class GenericCake<T> extends GenericBakedGood<GenericCake<T>, T> {

	public GenericCake<T> addSugar() {
		System.out.println("adding sugar");
		return this;
	}

	public GenericCake<T> addFlour() {
		System.out.println("adding flour");
		return this;
	}

	public GenericCake<T> mix() {
		System.out.println("mixing");
		return this;
	}

	@Override
	public GenericCake<T> bake() {
		System.out.println("baking at 325 degrees");
		return this;
	}
}