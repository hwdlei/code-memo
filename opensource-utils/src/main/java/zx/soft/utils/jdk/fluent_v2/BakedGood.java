package zx.soft.utils.jdk.fluent_v2;

public abstract class BakedGood<CHILD extends BakedGood<CHILD>> {

	@SuppressWarnings("unchecked")
	public CHILD prepare() {
		System.out.println("preparing the kitchen");
		return (CHILD) this;
	}

	@SuppressWarnings("unchecked")
	public CHILD eat() {
		String string = new StringBuilder().append("Mmm, this ").append(this.getClass().getSimpleName())
				.append(" is so tasty!").toString();

		System.out.println(string);
		return (CHILD) this;
	}

	@SuppressWarnings("unchecked")
	public abstract CHILD bake();
}