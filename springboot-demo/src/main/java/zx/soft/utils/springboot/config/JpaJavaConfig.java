package zx.soft.utils.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * EnableJpaRepositories(queryLookupStrategy = Key.CREATE_IF_NOT_FOUND)
 * queryLookupStrategy ： 自定义创建实例策略
 * basePackages： repository根目录
 * @author donglei
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "zx.soft.utils.springboot.dao", queryLookupStrategy = Key.CREATE_IF_NOT_FOUND)
public class JpaJavaConfig {

	@Autowired
	Environment env;

	/**
	 * application.properties 中已经配置好
	 */
	@Autowired
	DataSource dataSource;

	/**
	   * DataSource definition for database connection. Settings are read from
	   * the application.properties file (using the env object).
	   */
	//	@Bean
	//	public DataSource dataSource() {
	//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
	//		dataSource.setDriverClassName(env.getProperty("db.driver"));
	//		dataSource.setUrl(env.getProperty("db.url"));
	//		dataSource.setUsername(env.getProperty("db.username"));
	//		dataSource.setPassword(env.getProperty("db.password"));
	//		return dataSource;
	//	}

	//	@Bean
	//	public EntityManagerFactory entityManagerFactory() {
	//
	//		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	//		vendorAdapter.setGenerateDdl(true);
	//		vendorAdapter.setShowSql(true);
	//		vendorAdapter.setDatabase(Database.MYSQL);
	//
	//		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	//		factory.setJpaVendorAdapter(vendorAdapter);
	//		factory.setPackagesToScan("zx.soft.utils.springboot.dao");
	//		factory.setDataSource(dataSource);
	//		factory.afterPropertiesSet();
	//
	//		return factory.getObject();
	//	}

	//	@Bean
	//	public PlatformTransactionManager transactionManager() {
	//
	//		JpaTransactionManager txManager = new JpaTransactionManager();
	//		txManager.setEntityManagerFactory(entityManagerFactory());
	//		return txManager;
	//	}

}