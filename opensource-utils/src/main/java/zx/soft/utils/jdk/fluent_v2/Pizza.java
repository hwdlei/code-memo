package zx.soft.utils.jdk.fluent_v2;

public class Pizza extends BakedGood<Pizza> {

	public Pizza addCheese() {
		System.out.println("adding cheese");
		return this;
	}

	public Pizza addSauce() {
		System.out.println("adding sauce");
		return this;
	}

	@Override
	public Pizza bake() {
		System.out.println("baking at 450 degrees");
		return this;
	}
}