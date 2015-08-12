package zx.soft.utils.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/* JavaBean class for serialization and deserialization with Jackson. */
public class JacksonBeanExample implements java.io.Serializable {

	/* Set the integer value. Jackson will use this during
	 * deserialization. */
	@JsonProperty("value")
	public void setIntValue(final int intValue) {
		this.intValue = intValue;
	}

	/* Get the integer value. Jackson will use this during
	 * serialization. */
	@JsonProperty("value")
	public int getIntValue() {
		return this.intValue;
	}

	/* Variable to store an integer value. */
	private int intValue;
}
