package edu.hfut.mybatis.mapper;

import edu.hfut.mybatis.domain.PersonDomain;
import edu.hfut.mybatis.domain.TestDomain;


public interface TestMapper {
	//	@Select("select * from test where id = #{id}")
	public PersonDomain selectTest(int id);

	public void insertTest(TestDomain test);

	public void deleteTest(int id);
}
