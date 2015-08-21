package zx.soft.utils.jdk.fluent_v3;

public class Muffin extends GenericBakedGood<Muffin, Muffin.Mixin> {
	public enum Mixin {
		Blueberries, Raspberries, Walnuts
	}

	public Muffin addFlour() {
		System.out.println("adding flour");
		return this;
	}

	public Muffin addEggs() {
		System.out.println("adding eggs");
		return this;
	}

	@Override
	public Muffin bake() {
		System.out.println("baking at 275 degrees");
		return this;
	}
}