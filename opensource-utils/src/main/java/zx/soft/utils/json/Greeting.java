package zx.soft.utils.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Greeting implements Serializable {

	private static final long serialVersionUID = 7323158524405992964L;

	private long id;
	private String content;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(getClass()).add("id", id).add("content", content).toString();
	}

	@JsonCreator
	private static Greeting getInstance(@JsonProperty("id") long id, @JsonProperty("content") String content) {
		return new Greeting(id, content);
	}

	private Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

}
