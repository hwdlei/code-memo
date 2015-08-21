package zx.soft.utils.jdk.fluent_v1;

public abstract class StandardBakedGood {

	public StandardBakedGood prepare() {
		System.out.println("preparing the kitchen");
		return this;
	}

	public StandardBakedGood bake() {
		System.out.println("baking");
		return this;
	}
}