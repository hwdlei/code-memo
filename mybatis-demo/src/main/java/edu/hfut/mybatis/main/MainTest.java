package edu.hfut.mybatis.main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import edu.hfut.mybatis.domain.PersonDomain;
import edu.hfut.mybatis.mapper.TestMapper;

public class MainTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		try {
			TestMapper tm = session.getMapper(TestMapper.class);
			PersonDomain td = tm.selectTest(1);
			//			TestDomain td = (TestDomain) session.selectOne("edu.hfut.mybatis.test.TestMapper.selectTest", 1);
			//			TestDomain td = new TestDomain(100, "hello world", 2000);
			System.out.println(td.toString());
			//			tm.insertTest(td);
			//			session.commit();
		} finally {
			session.close();
		}
	}

}
