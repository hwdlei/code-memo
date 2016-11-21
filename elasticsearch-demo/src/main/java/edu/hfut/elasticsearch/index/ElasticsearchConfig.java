package edu.hfut.elasticsearch.index;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取全局配置
 * @author donglei
 * @date: 2016年8月15日 下午10:04:23
 */
public class ElasticsearchConfig {

	private static Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

	public static final String OUTPUT_DIR = "output.dir";

	private static Properties WHOLE_PROPS = new Properties();

	static {
		logger.info("Load resource: es.properties");
		try (InputStream in = ElasticsearchConfig.class.getClassLoader().getResourceAsStream("es.properties");) {
			WHOLE_PROPS.load(in);
		} catch (final Exception e) {
			logger.error("Exception:{}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static String getProps(String configName, String defaultValue) {
		return WHOLE_PROPS.getProperty(configName, defaultValue);
	}

}