package edu.hfut.mybatis.domain;

public class TestDomain {
	private int id;
	private String name;
	private int year;

	public TestDomain(int id, String name, int year) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.year = year;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id + this.name + this.year;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
