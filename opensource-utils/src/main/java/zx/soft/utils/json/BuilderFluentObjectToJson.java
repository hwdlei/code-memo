package zx.soft.utils.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class BuilderFluentObjectToJson {
	private String name;
	private int age;

	@JsonCreator
	private BuilderFluentObjectToJson(@JsonProperty("name") String name, @JsonProperty("age") int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(BuilderFluentObjectToJson.class).add("name", name).add("age", age).toString();
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	private BuilderFluentObjectToJson(Builder builder) {
		this.name = builder.name;
		this.age = builder.age;
	}

	public static class Builder {
		private String name;
		private int age;

		public Builder() {

		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withAge(int age) {
			this.age = age;
			return this;
		}

		public BuilderFluentObjectToJson build() {
			return new BuilderFluentObjectToJson(this);
		}
	}

}
