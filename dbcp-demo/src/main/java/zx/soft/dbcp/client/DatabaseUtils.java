package zx.soft.dbcp.client;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.ExceptionHelper;

/**
 * 数据库操作接口
 * @author donglei
 * @date: 2016年1月19日 下午10:59:54
 */
public class DatabaseUtils {

	private static Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

	/**
	 * 执行查询语句
	 * @param sql 执行SQL语句
	 * @param cls 查询返回结果映射类，要求T的属性名与数据库表字段一致
	 * @return
	 */
	public static <T> List<T> execQuery(String sql, Class<T> cls) {
		List<T> ts = new ArrayList<>();
		try (Connection conn = DatabaseConnectionFactory.getInstance().getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				try {
					T t = cls.newInstance();
					Field[] fields = cls.getDeclaredFields();
					for (Field f : fields) {
						f.setAccessible(true);
						if (result.getObject(f.getName()) != null) {
							f.set(t, result.getObject(f.getName()));
						}
					}
					ts.add(t);
				} catch (Exception e) {
					logger.error(ExceptionHelper.stackTrace(e));
				}

			}
		} catch (SQLException e) {
			logger.error("Exception:{}", ExceptionHelper.stackTrace(e));
		}

		return ts;
	}

	/**
	 * 执行更新语句
	 * @param sql SQL语句
	 * @return
	 */
	public static int execUpdate(String sql) {
		try (Connection conn = DatabaseConnectionFactory.getInstance().getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
			return statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Exception:{}", ExceptionHelper.stackTrace(e));
		}
		return -1;
	}

}
