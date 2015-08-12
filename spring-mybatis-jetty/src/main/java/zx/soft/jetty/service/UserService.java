package zx.soft.jetty.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zx.soft.jetty.dao.UserMapper;
import zx.soft.jetty.model.User;

@Service
public class UserService {

	@Inject
	private UserMapper userMapper;

	public User add(User user) {
		user.defaultValue();
		userMapper.add(user);
		return getUser(user.getUid(), user.getMid());
	}

	public void checkMid(long uid, long mid) {
		if (userMapper.get(uid, mid) == null) {
			throw new IllegalArgumentException("user is not exist. uid=" + uid + ", mid=" + mid);
		}
	}

	public User getUser(long uid, long mid) {
		User user = userMapper.get(uid, mid);
		if (user == null) {
			return null;
		}
		return user;
	}

	public User update(User user) {
		userMapper.update(user);
		return getUser(user.getUid(), user.getMid());
	}

	public void deleteAllByUidAndMid(long uid, long mid) {
		userMapper.delete(uid, mid);
	}

	public int queryUserCountByUid(long uid) {
		return userMapper.queryCountByUid(uid);
	}

	public int queryGenderByUid(long uid, long mid) {
		return userMapper.queryGenderByUid(uid, mid);
	}

}
