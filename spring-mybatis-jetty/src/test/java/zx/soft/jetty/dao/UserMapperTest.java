package zx.soft.jetty.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import zx.soft.jetty.model.User;
import zx.soft.jetty.model.UserQueryCondition;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/webapp/WEB-INF/applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager")
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserMapperTest {

	final long uid_1 = 1;
	final long uid_2 = 2;
	final String user_101 = "User:{uid=1, mid=100, name=张三, nick=张三昵称, gender=0}";
	final String user_102 = "User:{uid=1, mid=101, name=李四, nick=李四昵称, gender=1}";
	final String user_103 = "User:{uid=1, mid=102, name=王五, nick=王五昵称, gender=0}";
	final String user_104 = "User:{uid=2, mid=103, name=张三马甲, nick=Orz, gender=0}";

	@Inject
	private UserMapper userMapper;

	@Test
	public void testAdd() {
		User user = new User().setUid(200).defaultValue();
		userMapper.add(user);
		assertEquals(user.toString(), userMapper.get(200, user.getMid()).toString());

		user = new User().setUid(200).setName("赵六").setNick("赵六昵称").setGender(2);
		userMapper.add(user);
		assertEquals(user.toString(), userMapper.get(200, user.getMid()).toString());
	}

	@Test
	public void testGetUser() {
		User user = userMapper.get(uid_1, 101);

		assertEquals(user_102, user.toString());
		assertNull(userMapper.get(uid_1, 12345678)); // 没有该用户
	}

	@Test
	public void testGetUsers() {
		UserQueryCondition condition = new UserQueryCondition().setUid(uid_1);
		List<User> users = userMapper.list(condition);

		assertEquals(3, users.size());
		assertEquals(user_103, users.get(0).toString());
		assertEquals(user_102, users.get(1).toString());
		assertEquals(user_101, users.get(2).toString());
	}

	@Test
	public void testGetUsers_gender() {
		UserQueryCondition condition = new UserQueryCondition().setUid(uid_1).setGender(0);
		List<User> users = userMapper.list(condition);
		assertEquals(2, users.size());
		assertEquals(user_103, users.get(0).toString());
		assertEquals(user_101, users.get(1).toString());
	}

	@Test
	public void testQueryCountByUid() {
		int count = userMapper.queryCountByUid(uid_1);
		assertEquals(3, count);
	}

	@Test
	public void testToJson() {
		User user = new User().setUid(1).setMid(100).setName("zhangsan").setGender(1);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			assertEquals(
					"{\"uid\":1,\"mid\":100,\"name\":\"zhangsan\",\"nick\":null,\"gender\":1,\"update_time\":null}",
					objectMapper.writeValueAsString(user));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate() {
		User user = userMapper.get(uid_2, 103);
		user.setNick("张三昵称").setGender(1);
		userMapper.update(user);
		assertEquals("User:{uid=2, mid=103, name=张三马甲, nick=张三昵称, gender=1}", userMapper.get(uid_2, 103).toString());

		user.setName(""); // 抹掉名字
		userMapper.update(user);
		assertEquals("User:{uid=2, mid=103, name=, nick=张三昵称, gender=1}", userMapper.get(uid_2, 103).toString());
	}

}
