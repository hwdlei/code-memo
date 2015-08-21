package zx.soft.utils.jdk.fluent_v3;

import zx.soft.utils.jdk.fluent_v2.BakedGood;

public abstract class GenericBakedGood<CHILD extends GenericBakedGood<CHILD, T>, T> extends BakedGood<CHILD> {

	@SuppressWarnings("unchecked")
	public CHILD addSecretIngredient(T ingredient) {
		System.out.println("adding some " + ingredient);
		return (CHILD) this;
	}
}