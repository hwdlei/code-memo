package edu.hfut.mybatis.domain;

public class PersonDomain {
	private int id;
	private PersonInfo info;

	public PersonDomain() {
	}

	public PersonDomain(int id, String name, int year) {
		this.id = id;
		info = new PersonInfo();
		info.setName(name);
		info.setYear(year);

	}

	public PersonDomain(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + info.getName() + info.getYear();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PersonInfo getInfo() {
		return info;
	}

	public void setInfo(PersonInfo info) {
		this.info = info;
	}



}
