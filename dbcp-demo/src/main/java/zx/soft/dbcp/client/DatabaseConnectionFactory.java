package zx.soft.dbcp.client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.config.ConfigUtil;
import zx.soft.utils.log.LogbackUtil;

/**
 * 数据库连接工厂类
 * @author donglei
 * @date: 2016年1月19日 下午9:08:35
 */
public class DatabaseConnectionFactory {

	private static Logger logger = LoggerFactory.getLogger(DatabaseConnectionFactory.class);

	private static DatabaseConnectionFactory connectionFactory = null;

	private BasicDataSource dataSource;

	private String db_driver;
	private String db_url;
	private String db_username;
	private String db_password;

	private DatabaseConnectionFactory() {
		dbInit();
		createDataSource();
	}

	public static DatabaseConnectionFactory getInstance() {
		if(connectionFactory == null){
			connectionFactory = new DatabaseConnectionFactory();
		}
		return connectionFactory;

	}

	/**
	 * 初始化数据库相关参数
	 */
	private void dbInit() {
		Properties props = ConfigUtil.getProps("dbcp.properties");
		this.db_driver = props.getProperty("db.driver");
		this.db_url = props.getProperty("db.url");
		this.db_username = props.getProperty("db.username");
		this.db_password = props.getProperty("db.password");
	}

	/**
	 * 链接数据库
	 */
	private void createDataSource() {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(this.db_driver);
		dataSource.setUrl(this.db_url);
		dataSource.setUsername(this.db_username);
		dataSource.setPassword(this.db_password);
		dataSource.setTestOnBorrow(true);
		dataSource.setValidationQuery("SELECT 1");
	}

	/**
	 * 获取链接
	 */
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			logger.info("Db close error.");
		}
	}

}
