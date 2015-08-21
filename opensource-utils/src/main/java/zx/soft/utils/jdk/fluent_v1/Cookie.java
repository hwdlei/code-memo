package zx.soft.utils.jdk.fluent_v1;

public class Cookie extends StandardBakedGood {

	public Cookie addFlour() {
		System.out.println("adding flour");
		return this;
	}

	public static void main(String[] args) {
		Cookie cookie = new Cookie();
		cookie.bake();
		//.addFlour()    // inaccessible!
		//.bake();
	}
}