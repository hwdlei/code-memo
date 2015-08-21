package zx.soft.utils.jdk.fluent_v2;

public class Cake extends BakedGood<Cake> {

	public Cake addSugar() {
		System.out.println("adding sugar");
		return this;
	}

	public Cake addFlour() {
		System.out.println("adding flour");
		return this;
	}

	public Cake mix() {
		System.out.println("mixing");
		return this;
	}

	@Override
	public Cake bake() {
		System.out.println("baking at 325 degrees");
		return this;
	}
}