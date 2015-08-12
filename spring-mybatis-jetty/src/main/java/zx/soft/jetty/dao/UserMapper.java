package zx.soft.jetty.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zx.soft.jetty.model.User;
import zx.soft.jetty.model.UserQueryCondition;

/**
 * 用户信息接口
 * 
 * @author wanggang
 *
 */
public interface UserMapper {

	/**
	 * 插入用户信息
	 */
	@Insert("INSERT INTO `user` (`uid`,`mid`,`name`,`nick`,`gender`,`update_time`) " //
			+ " VALUES (#{uid},#{mid},#{name},#{nick},#{gender},now())")
	@Options(useGeneratedKeys = true, keyProperty = "mid")
	void add(User user);

	/**
	 * 更新用户更新信息
	 */
	@Update("UPDATE `user` SET `update_time` = now() WHERE `uid` = #{0} AND `mid` = #{1}")
	void exitMember(long uid, long mid);

	/**
	 * 获取用户信息
	 */
	@Select("SELECT `uid`,`mid`,`name`,`nick`,`gender`,`update_time` " //
			+ " FROM `user` WHERE `uid` = #{0} AND `mid` = #{1}")
	User get(long uid, long mid);

	/**
	 * 查询某个用户的性别
	 */
	@Select("SELECT `gender` FROM `user` WHERE `uid` = #{0} AND `mid` = #{1}")
	int queryGenderByUid(long uid, long mid);

	/**
	 * 统计满足条件的用户列表
	 */
	List<User> list(UserQueryCondition userQueryCondition);

	/**
	 * 更新用户信息
	 */
	int update(User user);

	/**
	 * 删除用户
	 */
	@Delete("DELETE FROM `user` WHERE `uid` = #{0} AND `mid` = #{1}")
	int delete(long uid, long mid);

	/**
	 * 统计某个用户的数量
	 */
	@Select("SELECT count(1) AS count FROM `user` WHERE `uid` = #{0}")
	int queryCountByUid(long uid);

}
