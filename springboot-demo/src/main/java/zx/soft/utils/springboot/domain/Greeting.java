package zx.soft.utils.springboot.domain;

import java.io.Serializable;
import java.util.Date;

public class Greeting implements Serializable {

	private static final long serialVersionUID = 7323158524405992964L;

	private long id;
	private String content;
	private Date date;

	public Greeting() {

	}

	public Greeting(long id, String content, Date date) {
		this.id = id;
		this.content = content;
		this.date = date;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

}
