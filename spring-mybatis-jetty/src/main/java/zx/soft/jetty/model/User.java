package zx.soft.jetty.model;

import java.util.Date;

import zx.soft.utils.json.JsonUtils;

/**
 * 用户信息表
 *
 * @author wanggang
 *
 */
public class User {

	private long uid;
	private long mid;
	private String name;
	private String nick;
	private int gender;
	private Date update_time;

	public User defaultValue() {
		if (name == null) {
			name = "";
		}
		if (nick == null) {
			nick = "";
		}
		if (update_time == null) {
			update_time = new Date();
		}
		return this;
	}

	public long getUid() {
		return uid;
	}

	public User setUid(long uid) {
		this.uid = uid;
		return this;
	}

	public long getMid() {
		return mid;
	}

	public User setMid(long mid) {
		this.mid = mid;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getNick() {
		return nick;
	}

	public User setNick(String nick) {
		this.nick = nick;
		return this;
	}

	public int getGender() {
		return gender;
	}

	public User setGender(int gender) {
		this.gender = gender;
		return this;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public User setUpdate_time(Date update_time) {
		this.update_time = update_time;
		return this;
	}

	@Override
	public String toString() {
		return "User:{uid=" + uid + ", mid=" + mid + ", name=" + name + ", nick=" + nick + ", gender=" + gender + "}";
	}

	public static void main(String args[]) {
		User s = new User();
		s.setUid(1);
		s.setMid(1);
		s.setName("zhangsan");
		s.setNick("lisi");
		s.setGender(0);
		s.setUpdate_time(new Date());
		System.out.println(JsonUtils.toJsonWithoutPretty(s));


	}
}
